package com.group.backend.crawler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;



import org.apache.http.Header;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import edu.uci.ics.crawler4j.crawler.CrawlController;

public class MainCrawler extends WebCrawler {

    private static final Pattern IMAGE_EXTENSIONS = Pattern.compile(".*\\.(bmp|gif|jpg|png)$");
    private AtomicInteger numSeenImages = new AtomicInteger();
    private CrawlController controller;

    public MainCrawler(AtomicInteger numSeenImages, CrawlController controller) {
        this.numSeenImages = numSeenImages;
        this.controller = controller;
    }

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        return href.startsWith("https://br.fashion");
    }

    @Override
    public void visit(Page page) {
        int docid = page.getWebURL().getDocid();
        String url = page.getWebURL().getURL();
        String domain = page.getWebURL().getDomain();
        String path = page.getWebURL().getPath();
        String subDomain = page.getWebURL().getSubDomain();
        String parentUrl = page.getWebURL().getParentUrl();
        String anchor = page.getWebURL().getAnchor();

        logger.debug("Docid: {}", docid);
        logger.info("URL: {}", url);
        logger.debug("Domain: '{}'", domain);
        logger.debug("Sub-domain: '{}'", subDomain);
        logger.debug("Path: '{}'", path);
        logger.debug("Parent page: {}", parentUrl);
        logger.debug("Anchor text: {}", anchor);

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String text = htmlParseData.getText();
            String html = htmlParseData.getHtml();
            Set<WebURL> links = htmlParseData.getOutgoingUrls();

            logger.debug("Text length: {}", text.length());
            logger.debug("Html length: {}", html.length());
            logger.debug("Number of outgoing links: {}", links.size());

            // Salvar o HTML em um arquivo
            saveHtmlToFile(url, html, "backend/src/main/java/com/group/backend/crawler/dadosCrawler/");

            // Adicionando novas seeds com base nos links encontrados
            for (WebURL link : links) {
                String newUrl = link.getURL();
                if (shouldVisit(page, link)){
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

    private void saveHtmlToFile(String url, String html, String outputDir) {
        String fileName = url.replaceAll("[^a-zA-Z0-9]", "_") + ".html";
        String filePath = outputDir + fileName;


        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(html);
            logger.info("HTML saved to {}", filePath);
        } catch (IOException e) {
            logger.error("Error saving HTML to file: {}", e.getMessage());
        }
    }
}
