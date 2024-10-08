package com.group.backend.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ParserHtml {
    public static void main(String[] args) {
        String filePath = "backend/src/main/java/com/group/backend/crawler/dadosCrawler/https___thenewscc_beehiiv_com_p_01_10_2024.html";

        try {
            // Ler o conteúdo do arquivo HTML
            String html = new String(Files.readAllBytes(Paths.get(filePath)));

            // Parsear o HTML
            Document doc = Jsoup.parse(html);

            // Extrair texto
            StringBuilder readableText = new StringBuilder();
            Elements paragraphs = doc.select("p");
            for (Element paragraph : paragraphs) {
                readableText.append(paragraph.text()).append("\n");
            }

            // Extrair links
            Elements links = doc.select("a");
            for (Element link : links) {
                readableText.append("Link: ").append(link.attr("href")).append(" (").append(link.text()).append(")\n");
            }

            // Imprimir o texto legível
            System.out.println(readableText.toString());

        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }
}

