package com.group.backend.crawler;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class AuthorExtractor {
    private static final Logger logger = LoggerFactory.getLogger(AuthorExtractor.class);

    public String extractAuthor(Document doc, String url) {
        if (url.contains("terra.com.br")) {
            return extractAuthorTerra(doc);
        } else if (url.contains("valor.globo.com")) {
            return extractAuthorValor(doc);
        } else if (url.contains("noticiasdatv.uol.com.br")) {
            return extractAuthorUOL(doc);
        } else if (url.contains("cartacapital.com.br")) {
            return extractAuthorCartaCapital(doc);
        } else if (url.contains("cnnbrasil.com.br")) {
            return extractAuthorCNN(doc);
        }
        return extractAuthorGeneric(doc);
    }

    private String extractAuthorTerra(Document doc) {
        // Tenta extrair o autor do primeiro seletor
        Element authorElement = doc.selectFirst("div.article__header__author-wrapper ul.article__header__author li.article__header__author__item span.article__header__author__item__name");
        if (authorElement != null && !authorElement.text().isEmpty()) {
            return authorElement.text();
        }
    
        // Alternativa: tenta extrair o autor usando outro seletor
        Element alternativeAuthorElement = doc.selectFirst("span.article__content--credit__source-text");
        if (alternativeAuthorElement != null && !alternativeAuthorElement.text().isEmpty()) {
            return alternativeAuthorElement.text().split(" - ")[0].trim(); // Extrai apenas a parte antes do ' - ' se necessário
        }
    
        return null;
    }

    private String extractAuthorValor(Document doc) {
        Element authorElement = doc.selectFirst("div.content-publication-data__from address[itemprop=author] span[itemprop=name]");
        
        if (authorElement == null) {
            authorElement = doc.selectFirst("div.content-publication-data__from address[itemprop=author] a[itemprop=url] span[itemprop=name]");
        }
        
        if (authorElement == null) {
            authorElement = doc.selectFirst("p.content-publication-data__from");
            if (authorElement != null) {
                String text = authorElement.text();
                
                if (text.startsWith("Por ")) {
                    text = text.replaceFirst("Por ", "").trim();
                }
                
                if (text.isEmpty() && authorElement.hasAttr("title")) {
                    text = authorElement.attr("title");
                }
                return text;
            }
        }
        
        return authorElement != null ? authorElement.text() : null;
    }

    private String extractAuthorUOL(Document doc) {
        Element authorElement = doc.selectFirst("div.row.perfil_autor.mb-4 div.autor_texto p strong");
        return authorElement != null ? authorElement.text() : null;
    }

    private String extractAuthorCartaCapital(Document doc) {
        Element authorElement = doc.selectFirst(".s-content__infos > span > strong > a");
        return authorElement != null ? authorElement.text() : null;
    }

    private String extractAuthorCNN(Document doc) {
        Element authorElement = doc.selectFirst("address.single-header__author p.author__name a");
        return authorElement != null ? authorElement.text() : null;
    }

    private String extractAuthorGeneric(Document doc) {
        Elements possibleAuthorElements = doc.select("[itemprop=author], .author, .byline, .reporter");
        for (Element element : possibleAuthorElements) {
            String text = element.text().trim();
            if (!text.isEmpty()) {
                return text;
            }
        }

        String regexText = doc.text();
        String authorRegex = "(?i)(autor|reporter|jornalista):?\\s*([A-ZÀ-ÿ][a-zà-ÿ]+(?:[-'\\s][A-ZÀ-ÿ][a-zà-ÿ]+)*)";
        Matcher matcher = Pattern.compile(authorRegex).matcher(regexText);
        return matcher.find() ? matcher.group(2) : null;
    }
}
