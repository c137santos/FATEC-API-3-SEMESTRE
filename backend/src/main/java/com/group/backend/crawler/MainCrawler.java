package com.group.backend.crawler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import org.apache.http.Header;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import com.group.backend.entity.Noticia;
import com.group.backend.entity.Reporter;
import com.group.backend.domain.NoticiaRepository;
import com.group.backend.domain.ReporterRepository;


public class MainCrawler extends WebCrawler {

    private static final Pattern IMAGE_EXTENSIONS = Pattern.compile(".*\\.(bmp|gif|jpg|png)$");
    private AtomicInteger numSeenImages = new AtomicInteger();
    private String seedUrl;
    private CrawlController controller;
    private Noticia noticia;
    private NoticiaRepository noticiaRepository;
    private ReporterRepository reporterRepository;
    private ParserHtml parserHtml;

    public MainCrawler(AtomicInteger numSeenImages, String seedUrl, CrawlController controller, Noticia noticia,
                       NoticiaRepository noticiaRepository, ParserHtml parserHtml, ReporterRepository reporterRepository) {
        this.numSeenImages = numSeenImages;
        this.seedUrl = seedUrl;
        this.controller = controller;
        this.noticia = noticia;
        this.noticiaRepository = noticiaRepository;
        this.parserHtml = parserHtml;
        this.reporterRepository = reporterRepository;
    }

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        return href.startsWith(seedUrl);
    }

    @Override
    public void visit(Page page) {
        int docid = page.getWebURL().getDocid();
        String url = page.getWebURL().getURL();

        logger.info("URL: {}", url);

        // Verifica se a URL já foi processada
        if (noticiaRepository.existsByUrl(url)) {
            logger.info("A URL já foi processada: {}", url);
            return; // Evita processamento duplicado
        }

        noticia.setUrl(url);

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String html = htmlParseData.getHtml();
            Set<WebURL> links = htmlParseData.getOutgoingUrls();

            logger.debug("Tamanho do HTML: {}", html.length());
            logger.debug("Número de links externos: {}", links.size());

            String filePath = saveHtmlToFile(url, html, "./src/main/java/com/group/backend/crawler/dadosCrawler/");

            try {
                parserHtml.parseAndDeleteFile(Paths.get(filePath), noticia);
            } catch (Exception e) {
                logger.error("Erro ao parsear o arquivo após salvar: {}", e.getMessage());
            }

            for (WebURL link : links) {
                String newUrl = link.getURL();
                if (shouldVisit(page, link)) {
                    controller.addSeed(newUrl);
                }
            }
        }

        Header[] responseHeaders = page.getFetchResponseHeaders();
        if (responseHeaders != null) {
            logger.debug("Headers de resposta:");
            for (Header header : responseHeaders) {
                logger.debug("\t{}: {}", header.getName(), header.getValue());
            }
        }

        logger.debug("=============");
    }

    private String saveHtmlToFile(String url, String html, String outputDir) {
        // Gera um nome de arquivo seguro, substituindo caracteres especiais
        String fileName = url.replaceAll("[^a-zA-Z0-9]", "_") + ".html";
        String filePath = outputDir + fileName;
    
        try {
            // Cria o diretório se ele não existir
            File directory = new File(outputDir);
            if (!directory.exists()) {
                boolean dirCreated = directory.mkdirs(); // Cria os diretórios
                if (dirCreated) {
                    logger.info("Diretório criado: {}", outputDir);
                } else {
                    logger.error("Erro ao criar o diretório: {}", outputDir);
                    return null; // Falha ao criar diretório, encerra o processo
                }
            }
    
            // Salva o arquivo HTML
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write(html);
                logger.info("HTML salvo em {}", filePath);
            }
    
        } catch (IOException e) {
            logger.error("Erro ao salvar HTML em arquivo: {}", e.getMessage());
            return null; // Falha ao salvar, retorna nulo
        }
    
        return filePath; // Retorna o caminho do arquivo salvo
    }
    
}
