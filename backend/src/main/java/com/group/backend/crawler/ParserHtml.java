package com.group.backend.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;

@Component
public class ParserHtml {

    public void parseAllFilesInFolder(String folderPath) {
        try {
            Files.list(Paths.get(folderPath))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".html"))
                    .forEach(this::parseAndDeleteFile);

        } catch (IOException e) {
            System.err.println("Erro ao listar ou processar arquivos no diretório: " + e.getMessage());
        }
    }

    private void parseAndDeleteFile(Path filePath) {
        try {
            System.out.println("Processando arquivo: " + filePath.getFileName());
    
            String html = new String(Files.readAllBytes(filePath));
            Document doc = Jsoup.parse(html);
    
            StringBuilder readableText = new StringBuilder();
    
            // Extraindo o texto inicial
            Elements paragraphs = doc.select("p");
            for (Element paragraph : paragraphs) {
                readableText.append(paragraph.text()).append("\n");
            }
    
            // Convertendo para String
            String textoCompleto = readableText.toString();
    
            // Variáveis para armazenar partes específicas
            String corpo = "";
            String jornalista = "";
            String data = "";
    
            // Captura o corpo da notícia até encontrar links
            corpo = textoCompleto.split("Link:")[0].trim(); // Assume que o corpo termina antes da seção "Link:"
    
            // Captura a data
            java.util.regex.Matcher dataMatcher = java.util.regex.Pattern.compile("(\\d{1,2} de \\w+ de \\d{4})").matcher(textoCompleto);
            if (dataMatcher.find()) {
                data = dataMatcher.group(1);
            }
    
            // Captura o jornalista (ajuste conforme necessário)
            java.util.regex.Matcher jornalistaMatcher = java.util.regex.Pattern.compile("([A-Za-zÀ-ÖØ-ÿ]+ \\s+[A-Za-zÀ-ÖØ-ÿ]+)").matcher(textoCompleto);
            if (jornalistaMatcher.find()) {
                jornalista = jornalistaMatcher.group(1);
            }
    
            System.out.println("Texto processado:\n" + textoCompleto);
            System.out.println("Corpo da notícia:\n" + corpo);
            System.out.println("Data:\n" + data);
            System.out.println("Jornalista:\n" + jornalista);
    
            textoCompleto = textoCompleto.replace(corpo, "").replace(data, "").replace(jornalista, "").trim();
    
            Files.delete(filePath);
            System.out.println("Arquivo processado e excluído: " + filePath.getFileName());
    
        } catch (IOException e) {
            System.err.println("Erro ao processar o arquivo " + filePath.getFileName() + ": " + e.getMessage());
        }
    }
}