package com.group.backend.crawler;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component // Adiciona esta anotação para registrar a classe como um bean no Spring
public class TextExtractor {
    private static final Logger logger = LoggerFactory.getLogger(TextExtractor.class);

    public String extractText(Document doc) {
        StringBuilder readableText = new StringBuilder();
        Elements paragraphs = doc.select("p");
        paragraphs.forEach(paragraph -> readableText.append(paragraph.text()).append("\n"));
        return readableText.toString();
    }

    public String extractCorpo(String textoCompleto) {
        try {
            String corpo = textoCompleto.split("Link:")[0].trim();
            logger.debug("Texto extraído para o corpo da notícia: {}", corpo);
            return corpo;
        } catch (Exception e) {
            logger.error("Erro ao extrair o corpo do texto: {}", e.getMessage());
            return "";
        }
    }
}
