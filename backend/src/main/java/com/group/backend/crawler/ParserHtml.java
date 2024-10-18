package com.group.backend.crawler;

import com.group.backend.domain.NoticiaRepository;
import com.group.backend.domain.TagRepository;
import com.group.backend.entity.Noticia;
import com.group.backend.entity.Regionalismo;
import com.group.backend.entity.Tag;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ParserHtml {

    @Autowired
    private NoticiaRepository noticiaRepository;

    @Autowired
    private TagRepository tagRepository;

    public void parseAllFilesInFolder(String folderPath, Noticia noticia) {
        try {
            Files.list(Paths.get(folderPath))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".html"))
                    .forEach(path -> parseAndDeleteFile(path, noticia));
            System.err.println("End of loop parser");
        } catch (IOException e) {
            System.err.println("Erro ao listar ou processar arquivos no diretório: " + e.getMessage());
        }
    }

    public void parseAndDeleteFile(Path filePath, Noticia noticia) {
        try {
            System.out.println("Processando arquivo: " + filePath.getFileName());
            String html = new String(Files.readAllBytes(filePath));
            Document doc = Jsoup.parse(html);
            String textoCompleto = extractText(doc);

            String corpo = extractCorpo(textoCompleto);
            String data = extractData(textoCompleto);
            noticia.setNotiId(null);

            LocalDate date = null;
            if (data != null) {
                date = parseDate(data);
                noticia.setNotiData(date);
            }

            if (noticiaRepository.existsByUrl(noticia.getUrl())) {
                System.out.println("Notícia com a URL já existente: " + noticia.getUrl());
                return;
            }

            if (checkTagsAndRegionalismosInCorpo(corpo)) {
                noticia.setNotiText(corpo);
                noticiaRepository.save(noticia);
            }
            
            Files.delete(filePath);
        } catch (IOException e) {
            System.err.println("Erro ao processar o arquivo " + filePath.getFileName() + ": " + e.getMessage());
        }
    }

    private String extractText(Document doc) {
        StringBuilder readableText = new StringBuilder();
        Elements paragraphs = doc.select("p");
        for (Element paragraph : paragraphs) {
            readableText.append(paragraph.text()).append("\n");
        }
        return readableText.toString();
    }

    private String extractCorpo(String textoCompleto) {
        try {
            return textoCompleto.split("Link:")[0].trim().substring(0, Math.min(245, textoCompleto.length())).trim();
        } catch (Exception e) {
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

    private boolean checkTagsAndRegionalismosInCorpo(String corpo) {
        List<Tag> tags = tagRepository.findAllWithRegionalismos(); // Busca todas as tags e seus regionalismos
        boolean existe = false;

        for (Tag tag : tags) {
            if (corpo.contains(tag.getTagNome())) {
                System.out.println("Tag encontrada: " + tag.getTagNome());
                existe = true;

                // Verifica se algum regionalismo relacionado à Tag está presente no corpo
                for (Regionalismo regionalismo : tag.listRegionalismos()) {
                    if (corpo.contains(regionalismo.getNome())) {
                        System.out.println("Regionalismo encontrado: " + regionalismo.getNome());
                    } else {
                        System.out.println("Regionalismo não encontrado: " + regionalismo.getNome());
                    }
                }
            } else {
                System.out.println("Tag não encontrada: " + tag.getTagNome());
            }
        }

        return existe;
    }
}
