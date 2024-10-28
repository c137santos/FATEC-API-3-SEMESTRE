package com.group.backend.crawler;

import com.group.backend.entity.Regionalismo;
import com.group.backend.domain.NoticiaRepository;
import com.group.backend.domain.ReporterRepository;
import com.group.backend.domain.TagRepository;
import com.group.backend.entity.Noticia;
import com.group.backend.entity.Reporter;
import com.group.backend.entity.Tag;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ParserHtml {

    private static final Logger logger = LoggerFactory.getLogger(ParserHtml.class);

    @Autowired
    private NoticiaRepository noticiaRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ReporterRepository reporterRepository;

    public void parseAllFilesInFolder(String folderPath, Noticia noticia) {
        try {
            Files.list(Paths.get(folderPath))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".html"))
                    .forEach(path -> parseAndDeleteFile(path, noticia));
            System.err.println("End of loop parser");
        } catch (IOException e) {
            System.err.println("Erro ao listar ou processar arquivos no diretório: " + e.getMessage());
            logger.error("Erro ao listar ou processar arquivos no diretório: {}", e.getMessage());
        }
    }

    public void parseAndDeleteFile(Path filePath, Noticia noticia) {
        logger.info("Iniciando parsing do arquivo: {}", filePath.getFileName());
        try {
            String html = new String(Files.readAllBytes(filePath));
            Document doc = Jsoup.parse(html);
            String textoCompleto = extractText(doc);

            // Extraindo corpo da notícia
            String corpo = extractCorpo(textoCompleto);
            if (corpo == null || corpo.isEmpty()) {
                logger.warn("Corpo da notícia não encontrado no arquivo: {}", filePath.getFileName());
                return;
            }
            logger.debug("Corpo extraído da notícia: {}", corpo);
            noticia.setNotiText(corpo);

            // Extraindo data
            String data = extractData(textoCompleto);
            LocalDate date = null;
            if (data != null) {
                date = parseDate(data);
                noticia.setNotiData(date);
                logger.info("Data extraída da notícia: {}", date);
            } else {
                logger.warn("Data não encontrada para a URL associada: {}", noticia.getUrl());
            }

            // Verificando e associando o repórter
            String reporterName = extractReporterName(textoCompleto);
            if (reporterName != null) {
                Reporter reporter = reporterRepository.findByNome(reporterName);
                if (reporter == null) {
                    reporter = new Reporter(reporterName, noticia.getPortal());
                    reporterRepository.save(reporter);
                }
                noticia.setReporte(reporter);
                logger.info("Repórter encontrado e associado: {}", reporterName);
            } else {
                logger.warn("Repórter não encontrado para a URL associada: {}", noticia.getUrl());
            }

            // Verificação de tags e regionalismos
            if (checkTagsAndRegionalismosInCorpo(corpo, noticia)) {
                logger.info("Tags e/ou regionalismos relevantes encontrados para a URL: {}", noticia.getUrl());
            } else {
                logger.warn("Nenhuma tag ou regionalismo relevante encontrado para a URL: {}. Notícia não salva.", noticia.getUrl());
            }

            // Verifica todos os dados essenciais antes de salvar
            if (data != null && reporterName != null && checkTagsAndRegionalismosInCorpo(corpo, noticia)) {
                noticiaRepository.save(noticia);
                logger.info("Notícia salva com sucesso: {}", noticia.getUrl());
            } else {
                logger.warn("Notícia ignorada por dados insuficientes: URL: {}, Data presente: {}, Repórter presente: {}, Tags encontradas: {}",
                        noticia.getUrl(), data != null, reporterName != null, checkTagsAndRegionalismosInCorpo(corpo, noticia));
            }

        } catch (IOException e) {
            logger.error("Erro ao processar o arquivo {}: {}", filePath.getFileName(), e.getMessage());
        } finally {
            try {
                Files.delete(filePath);
                logger.info("Arquivo deletado após parsing: {}", filePath.getFileName());
            } catch (IOException e) {
                logger.error("Erro ao deletar o arquivo {}: {}", filePath.getFileName(), e.getMessage());
            }
        }
    }

    private String extractText(Document doc) {
        StringBuilder readableText = new StringBuilder();
        Elements paragraphs = doc.select("p");
        paragraphs.forEach(paragraph -> readableText.append(paragraph.text()).append("\n"));
        return readableText.toString();
    }

    private String extractCorpo(String textoCompleto) {
        try {
            return textoCompleto.split("Link:")[0].trim();
        } catch (Exception e) {
            logger.error("Erro ao extrair o corpo do texto: {}", e.getMessage());
            return "";
        }
    }

    private String extractData(String textoCompleto) {
        String regex = "(\\d{1,2} 'de' \\w+ 'de' \\d{4})|(\\d{1,2}/\\d{1,2}/\\d{4})|(\\d{2}/\\d{2}/\\d{4})|(\\d{4}-\\d{2}-\\d{2})|(\\w+ \\d{1,2}, \\d{4})|(\\d{1,2} 'de' \\w+ 'de' \\d{4})";
        Matcher dataMatcher = Pattern.compile(regex).matcher(textoCompleto);
        return dataMatcher.find() ? dataMatcher.group(0) : null;
    }

    private LocalDate parseDate(String dataStr) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendOptional(DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy"))
                .appendOptional(DateTimeFormatter.ofPattern("d/MM/yyyy"))
                .appendOptional(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                .appendOptional(DateTimeFormatter.ofPattern("MMMM d, yyyy"))
                .toFormatter();

        return LocalDate.parse(dataStr, formatter);
    }

    private String extractReporterName(String textoCompleto) {
        String regex = "(?i)(autor|reporter|jornalista):?\\s*([A-ZÀ-ÿ][a-zà-ÿ]+(?:[-'\\s][A-ZÀ-ÿ][a-zà-ÿ]+)*)";
        Matcher matcher = Pattern.compile(regex).matcher(textoCompleto);
        return matcher.find() ? matcher.group(2) : null;
    }

    private boolean checkTagsAndRegionalismosInCorpo(String corpo, Noticia noticia) {
        List<Tag> tags = tagRepository.findAllWithRegionalismos();
        boolean existeTagRelevante = false;

        for (Tag tag : tags) {
            if (corpo.contains(tag.getTagNome())) {
                logger.info("Tag '{}' encontrada no corpo da notícia da URL {}", tag.getTagNome(), noticia.getUrl());
                existeTagRelevante = true;

                for (Regionalismo regionalismo : tag.listRegionalismos()) {
                    if (corpo.contains(regionalismo.getNome())) {
                        logger.info("Regionalismo '{}' encontrado para a tag '{}' na URL {}", regionalismo.getNome(), tag.getTagNome(), noticia.getUrl());
                    } else {
                        logger.debug("Regionalismo '{}' não encontrado para a tag '{}' na URL {}", regionalismo.getNome(), tag.getTagNome(), noticia.getUrl());
                    }
                }
            } else {
                logger.debug("Tag '{}' não encontrada no corpo da notícia da URL {}", tag.getTagNome(), noticia.getUrl());
            }
        }
        return existeTagRelevante;
    }
}
