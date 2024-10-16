package com.group.backend.crawler;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.group.backend.domain.NoticiaRepository;
import com.group.backend.entity.Noticia;

@Component
public class ParserHtml {

    @Autowired
    private NoticiaRepository noticiaRepository;

    public void parseAllFilesInFolder(String folderPath) {
        try {
            Files.list(Paths.get(folderPath))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".html"))
                    .forEach(this::parseAndDeleteFile);
            System.err.println("End of loop parser");
        } catch (IOException e) {
            System.err.println("Erro ao listar ou processar arquivos no diretório: " + e.getMessage());
        }
    }

    private void parseAndDeleteFile(Path filePath) {
        try {
            System.out.println("Processando arquivo: " + filePath.getFileName());
            String html = new String(Files.readAllBytes(filePath));
            Document doc = Jsoup.parse(html);
            String textoCompleto = extractText(doc);

            LocalDate date = null;
            String corpo = extractCorpo(textoCompleto);
            String data = extractData(textoCompleto);
            String jornalista = extractJornalista(textoCompleto);

            if (data != null) {
                date = parseDate(data);
            }

            if (corpo.isEmpty() || date == null) {
                Files.delete(filePath);
                return;
            }

            System.out.println("Texto processado:\n" + textoCompleto);
            System.out.println("Corpo da notícia:\n" + corpo);
            System.out.println("Data:\n" + data);
            System.out.println("Jornalista:\n" + jornalista);

            Noticia noticia = new Noticia();
            noticia.setNotiData(date);
            noticia.setNotiText(corpo);
            noticiaRepository.save(noticia);

            Files.delete(filePath);
            System.out.println("Arquivo processado e excluído: " + filePath.getFileName());

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
            return textoCompleto.split("Link:")[0].trim(); // Assume que o corpo termina antes da seção "Link:"
        } catch (Exception e) {
            return "";
        }
    }

    private String extractData(String textoCompleto) {
        Matcher dataMatcher = Pattern.compile("(\\d{1,2} de \\w+ de \\d{4})").matcher(textoCompleto);
        return dataMatcher.find() ? dataMatcher.group(1) : null;
    }

    private String extractJornalista(String textoCompleto) {
        Matcher jornalistaMatcher = Pattern.compile("([A-Za-zÀ-ÖØ-ÿ]+ \\s+[A-Za-zÀ-ÖØ-ÿ]+)").matcher(textoCompleto);
        return jornalistaMatcher.find() ? jornalistaMatcher.group(1) : null;
    }

    private LocalDate parseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy");
        return LocalDate.parse(date, formatter);
    }
}
