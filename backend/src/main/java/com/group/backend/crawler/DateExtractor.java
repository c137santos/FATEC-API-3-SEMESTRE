package com.group.backend.crawler;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

@Component 
public class DateExtractor {
    private static final Logger logger = LoggerFactory.getLogger(DateExtractor.class);

    // Construtor
    public DateExtractor() {
        logger.info("DateExtractor bean instanciado com sucesso.");
    }

    public LocalDate extractDate(Document doc, String url) {
        String dateStr = null;

        if (url.contains("cnnbrasil.com.br")) {
            dateStr = extractDateCNN(doc);
        } else if (url.contains("terra.com.br")) {
            dateStr = extractDateTerra(doc);
        } else if (url.contains("valor.globo.com")) {
            dateStr = extractDateValor(doc);
        }

        if (dateStr != null) {
            return parseDate(dateStr);
        }
        return null;
    }

   private String extractDateCNN(Document doc) {
    Element dateElement = doc.selectFirst("time.single-header__time");
    if (dateElement != null) {
        String dateText = dateElement.text();
        List<String> dateFormats = Arrays.asList(
            "dd/MM/yyyy 'às' HH:mm", 
            "dd/MM/yyyy 'às' HH:mm | 'Atualizado' dd/MM/yyyy 'às' HH:mm"
        );

        for (String format : dateFormats) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                LocalDateTime date = LocalDateTime.parse(dateText, formatter);
                return date.toString(); // Formato ISO (opcional, pode ajustar)
            } catch (DateTimeParseException e) {
                // Log de erro para identificar falhas de parsing
                LoggerFactory.getLogger(this.getClass()).error("Erro ao parsear a data '{}'", dateText);
            }
        }
    }
    return null;
}

    private String extractDateTerra(Document doc) {
        // Primeira tentativa: extrair a data do elemento div.date
        Element dateElement = doc.selectFirst("div.article__header__info__datetime div.date");
        if (dateElement != null) {
            return dateElement.text().replace(" - ", "");
        }
    
        // Segunda tentativa: extrair a data do span dentro de div.date
        Element dayMonthElement = doc.selectFirst("div.date span.day-month");
        Element yearElement = doc.selectFirst("div.date span.year");
        Element timeElement = doc.selectFirst("div.date span.time");
        if (dayMonthElement != null && yearElement != null && timeElement != null) {
            return dayMonthElement.text() + " " + yearElement.text() + " " + timeElement.text().replace(" - ", "");
        }
    
        // Terceira tentativa: extrair do meta itemprop="datePublished"
        Element metaDatePublished = doc.selectFirst("meta[itemprop='datePublished']");
        if (metaDatePublished != null && metaDatePublished.hasAttr("content")) {
            return metaDatePublished.attr("content");
        }
    
        // Quarta tentativa: extrair do meta itemprop="dateModified" se datePublished não estiver disponível
        Element metaDateModified = doc.selectFirst("meta[itemprop='dateModified']");
        if (metaDateModified != null && metaDateModified.hasAttr("content")) {
            return metaDateModified.attr("content");
        }
    
        // Quinta tentativa: capturar a data usando o atributo datetime em div com classe específica
        Element datetimeElement = doc.selectFirst("div.article_header_info_datetime time");
        if (datetimeElement != null && datetimeElement.hasAttr("datetime")) {
            return datetimeElement.attr("datetime");
        }
    
        // Sexta tentativa: extrair diretamente do div com classe 'article_header_info' se existir
        Element headerInfoDate = doc.selectFirst("div.article_header_info");
        if (headerInfoDate != null) {
            return headerInfoDate.text();
        }
    
        // Sétima tentativa: usar uma combinação de seletores para capturar a data
        Element combinedDateElement = doc.selectFirst("meta[itemprop='datePublished'], div.date span.time");
        if (combinedDateElement != null) {
            return combinedDateElement.text();
        }
    
        // Oitava tentativa: verificar se existe um meta tag genérico com name='date'
        Element metaDate = doc.selectFirst("meta[name='date']");
        if (metaDate != null && metaDate.hasAttr("content")) {
            return metaDate.attr("content");
        }
    
        // Nona tentativa: capturar elementos de divs relacionadas à data
        Element flexDate = doc.selectFirst("div.article_header_info_datetime.flex");
        if (flexDate != null) {
            return flexDate.text();
        }
    
        // Décima tentativa: usar outras divs ou spans que possam conter a data de publicação
        Element backupDate = doc.selectFirst("div.date > span");
        if (backupDate != null) {
            return backupDate.text();
        }
    
        // Retorna null caso nenhuma das alternativas capture a data
        return null;
    }

    private String extractDateValor(Document doc) {
        // Primeira tentativa: selecionar o elemento com itemprop 'datePublished' e pegar o texto
        Element dateElement = doc.selectFirst("time[itemprop='datePublished']");
        if (dateElement != null) {
            String dateText = dateElement.text();
            if (dateText.isEmpty()) {
                dateText = dateElement.attr("datetime");
            }
            if (dateText != null && !dateText.isEmpty()) {
                return dateText;
            }
        }

        // Segunda tentativa: verificar se há outra instância de meta com itemprop 'datePublished'
        Element metaDatePublished = doc.selectFirst("meta[itemprop='datePublished']");
        if (metaDatePublished != null && metaDatePublished.hasAttr("content")) {
            return metaDatePublished.attr("content");
        }

        // Terceira tentativa: verificar meta com itemprop 'dateModified' como fallback
        Element metaDateModified = doc.selectFirst("meta[itemprop='dateModified']");
        if (metaDateModified != null && metaDateModified.hasAttr("content")) {
            return metaDateModified.attr("content");
        }

        // Quarta tentativa: capturar o conteúdo de outros elementos relevantes na hierarquia
        Element alternateDateElement = doc.selectFirst("div.content-publication-data__text time");
        if (alternateDateElement != null) {
            String alternateDateText = alternateDateElement.text();
            if (alternateDateText.isEmpty()) {
                alternateDateText = alternateDateElement.attr("datetime");
            }
            return alternateDateText != null && !alternateDateText.isEmpty() ? alternateDateText : null;
        }

        // Retorna null caso nenhuma das alternativas capture a data
        return null;
    }

    private LocalDate parseDate(String dataStr) {
        dataStr = dataStr.replaceAll("(\\d{2})h(\\d{2})", "$1:$2");
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendOptional(DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy HH:mm"))
                .appendOptional(DateTimeFormatter.ofPattern("d/MM/yyyy HH:mm"))
                .appendOptional(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                .appendOptional(DateTimeFormatter.ofPattern("d/MM/yyyy"))
                .appendOptional(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
                .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                .appendOptional(DateTimeFormatter.ofPattern("d MMM yyyy HH:mm"))
                .appendOptional(DateTimeFormatter.ofPattern("d MMM yyyy"))
                .appendOptional(DateTimeFormatter.ofPattern("d MMM yyyyHH:mm"))
                .toFormatter();
        try {
            return LocalDate.parse(dataStr, formatter);
        } catch (Exception e) {
            logger.error("Erro ao parsear a data '{}'", dataStr);
            return null;
        }
    }
}
