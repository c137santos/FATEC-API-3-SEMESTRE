package com.group.backend.crawler;

import com.group.backend.entity.Noticia;
import com.group.backend.entity.TagNoticia;
import com.group.backend.entity.Reporter;
import com.group.backend.entity.Tag;
import com.group.backend.domain.NoticiaRepository;
import com.group.backend.domain.ReporterRepository;
import com.group.backend.domain.TagNoticiaRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
public class HtmlParserService {
    private static final Logger logger = LoggerFactory.getLogger(HtmlParserService.class);

    @Autowired
    private TextExtractor textExtractor;

    @Autowired
    @Lazy
    private DateExtractor dateExtractor;

    @Autowired
    private AuthorExtractor authorExtractor;

    @Autowired
    private TagChecker tagChecker;

    @Autowired
    private NoticiaRepository noticiaRepository;

    @Autowired
    private ReporterRepository reporterRepository;

    @Autowired
    private TagNoticiaRepository tagNoticiaRepository;

    private static final boolean dataRequired = false;
    private static final boolean reporterNameRequired = false;

    public HtmlParserService() {
        logger.info("HtmlParserService bean instanciado com sucesso.");
    }

    public void parseAllFilesInFolder(String folderPath, Noticia noticia) {
        logger.info("Iniciando o parsing de todos os arquivos no diretório: {}", folderPath);
        try {
            Files.list(Paths.get(folderPath))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".html"))
                    .forEach(path -> parseAndDeleteFile(path, noticia));
        } catch (IOException e) {
            logger.error("Erro ao listar ou processar arquivos no diretório {}: {}", folderPath, e.getMessage(), e);
        }
    }

    public void parseAndDeleteFile(Path filePath, Noticia noticia) {
        logger.info("Iniciando parsing do arquivo: {}", filePath.getFileName());
        try {
            String html = new String(Files.readAllBytes(filePath));
            logger.debug("HTML do arquivo {} lido com sucesso, tamanho: {} caracteres", filePath.getFileName(), html.length());

            Document doc = Jsoup.parse(html);
            logger.debug("Documento HTML parseado com sucesso.");

            doc.select("div.barrer").remove();
            logger.debug("Elementos 'div.barrer' removidos do documento HTML.");

            String textoCompleto = textExtractor.extractText(doc);
            logger.debug("Texto completo extraído com sucesso, tamanho: {} caracteres", textoCompleto.length());

            String corpo = textExtractor.extractCorpo(textoCompleto);
            if (corpo == null || corpo.isEmpty()) {
                logger.warn("Corpo da notícia não encontrado no arquivo: {}", filePath.getFileName());
                return;
            }
            noticia.setNotiText(corpo);
            logger.debug("Corpo da notícia setado com sucesso.");

            LocalDate date = dateExtractor.extractDate(doc, noticia.getUrl());
            if (date != null) {
                noticia.setNotiData(date);
                logger.info("Data extraída da notícia: {}", date);
            } else {
                logger.warn("Não foi possível extrair a data da notícia do arquivo: {}", filePath.getFileName());
            }

            String reporterName = authorExtractor.extractAuthor(doc, noticia.getUrl());
            if (reporterName != null) {
                logger.info("Nome do repórter extraído: {}", reporterName);
                Reporter reporter = reporterRepository.findByNome(reporterName);
                if (reporter == null) {
                    reporter = new Reporter(reporterName, noticia.getPortal());
                    reporterRepository.save(reporter);
                    logger.info("Novo repórter salvo no repositório: {}", reporterName);
                } else {
                    logger.debug("Repórter já existe no repositório: {}", reporterName);
                }
                noticia.setReporte(reporter);
                logger.info("Repórter associado à notícia: {}", reporterName);
            } else {
                logger.warn("Não foi possível extrair o nome do repórter do arquivo: {}", filePath.getFileName());
            }

            if ((!dataRequired || date != null) &&
                (!reporterNameRequired || reporterName != null) &&
                tagChecker.checkTagsAndRegionalismosInCorpo(corpo, noticia)) {

                logger.info("Verificando existência de notícia com a URL: {}", noticia.getUrl());
                Noticia existingNoticia = noticiaRepository.findByUrl(noticia.getUrl());

                if (existingNoticia != null) {
                    logger.info("Notícia existente encontrada: ID={}, URL={}", existingNoticia.getNotiId(), existingNoticia.getUrl());
                    if (!existingNoticia.getNotiText().equals(noticia.getNotiText()) || 
                        (existingNoticia.getNotiData() != null && !existingNoticia.getNotiData().equals(noticia.getNotiData())) || 
                        (existingNoticia.getReporte() != null && !existingNoticia.getReporte().equals(noticia.getReporte()))) {

                        Noticia newNoticia = new Noticia();
                        newNoticia.setNotiId(null); // Garante que o ID seja nulo para nova inserção
                        newNoticia.setUrl(noticia.getUrl());
                        newNoticia.setNotiText(noticia.getNotiText());
                        newNoticia.setNotiData(noticia.getNotiData());
                        newNoticia.setReporte(noticia.getReporte());
                        newNoticia.setPorId(noticia.getPorId());

                        noticiaRepository.save(newNoticia);
                        logger.info("Nova notícia salva: URL={}, ID={}", newNoticia.getUrl(), newNoticia.getNotiId());

                        saveTagsForNoticia(newNoticia, corpo);
                    } else {
                        logger.info("Notícia já existente e com conteúdo igual. Nenhuma nova entrada foi criada: URL={}", noticia.getUrl());
                    }
                } else {
                    noticia.setNotiId(null); // Garante que o ID seja nulo para nova inserção
                    noticiaRepository.save(noticia);
                    logger.info("Notícia nova salva: ID={}, URL={}", noticia.getNotiId(), noticia.getUrl());

                    saveTagsForNoticia(noticia, corpo);
                }
            } else {
                logger.warn("Notícia ignorada por dados insuficientes: URL: {}, Data presente: {}, Repórter presente: {}, Tags encontradas: {}",
                        noticia.getUrl(), date != null, reporterName != null, tagChecker.checkTagsAndRegionalismosInCorpo(corpo, noticia));
            }

        } catch (IOException e) {
            logger.error("Erro ao processar o arquivo {}: {}", filePath.getFileName(), e.getMessage(), e);
        } finally {
            try {
                Files.delete(filePath);
                logger.info("Arquivo deletado após parsing: {}", filePath.getFileName());
            } catch (IOException e) {
                logger.error("Erro ao deletar o arquivo {}: {}", filePath.getFileName(), e.getMessage(), e);
            }
        }
    }

    private void saveTagsForNoticia(Noticia noticia, String corpo) {
        List<Tag> tags = tagChecker.extractTags(corpo, noticia);

        for (Tag tag : tags) {
            TagNoticia tagNoticia = new TagNoticia();

            tagNoticia.setTagId(tag);
            tagNoticia.setNotiId(noticia);

            tagNoticiaRepository.save(tagNoticia);
        }
    }
}
