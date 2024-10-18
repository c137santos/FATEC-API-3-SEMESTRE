package com.group.backend.crawler;

import java.io.BufferedWriter;
import java.io.FileWriter;
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
import com.group.backend.domain.NoticiaRepository;

public class MainCrawler extends WebCrawler {

    private static final Pattern IMAGE_EXTENSIONS = Pattern.compile(".*\\.(bmp|gif|jpg|png)$");
    private AtomicInteger numSeenImages = new AtomicInteger();
    private String seedUrl;
    private CrawlController controller;
    private Noticia noticia;
    private NoticiaRepository noticiaRepository; // Repositório para verificar a URL
    private ParserHtml parserHtml;

    public MainCrawler(AtomicInteger numSeenImages, String seedUrl, CrawlController controller, Noticia noticia, NoticiaRepository noticiaRepository, ParserHtml parserHtml) {
        this.numSeenImages = numSeenImages;
        this.controller = controller;
        this.seedUrl = seedUrl;
        this.noticia = noticia;
        this.noticiaRepository = noticiaRepository; // Inicializando o repositório
        this.parserHtml = parserHtml;
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

        // Verifica se a URL já foi salva no banco de dados
        if (noticiaRepository.existsByUrl(url)) {
            logger.info("A URL já foi processada: {}", url);
            return; // Se já existe, não processa novamente
        }

        noticia.setUrl(url);

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String html = htmlParseData.getHtml();
            Set<WebURL> links = htmlParseData.getOutgoingUrls();

            logger.debug("Html length: {}", html.length());
            logger.debug("Number of outgoing links: {}", links.size());

            // Salvar o HTML em um arquivo
            String filePath = saveHtmlToFile(url, html, "backend/src/main/java/com/group/backend/crawler/dadosCrawler/");

            // Parsear e deletar o arquivo imediatamente após salvar
            try {
                parserHtml.parseAndDeleteFile(Paths.get(filePath), noticia);
            } catch (Exception e) {
                logger.error("Erro ao parsear o arquivo após salvar: {}", e.getMessage());
            }

            // Adicionando novas seeds com base nos links encontrados
            for (WebURL link : links) {
                String newUrl = link.getURL();
                if (shouldVisit(page, link)) {
                    controller.addSeed(newUrl);
                }
            }
        }

        // Exibindo headers de resposta
        Header[] responseHeaders = page.getFetchResponseHeaders();
        if (responseHeaders != null) {
            logger.debug("Response headers:");
            for (Header header : responseHeaders) {
                logger.debug("\t{}: {}", header.getName(), header.getValue());
            }
        }

        logger.debug("=============");
    }

    private String saveHtmlToFile(String url, String html, String outputDir) {
        String fileName = url.replaceAll("[^a-zA-Z0-9]", "_") + ".html";
        String filePath = outputDir + fileName;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(html);
            logger.info("HTML saved to {}", filePath);
        } catch (IOException e) {
            logger.error("Error saving HTML to file: {}", e.getMessage());
        }

        return filePath; // Retorne o caminho do arquivo salvo
    }
}
