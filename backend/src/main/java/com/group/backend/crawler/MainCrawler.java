package com.group.backend.crawler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
import com.group.backend.domain.ReporterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainCrawler extends WebCrawler {

    private static final Logger logger = LoggerFactory.getLogger(MainCrawler.class);
    private static final Pattern IMAGE_EXTENSIONS = Pattern.compile(".*\\.(bmp|gif|jpg|png|svg|woff2)$");
    private static final int MAX_HTML_SIZE = 10 * 1024 * 1024; // 10 MB
    private AtomicInteger numSeenImages;
    private String seedUrl;
    private CrawlController controller;
    private Noticia noticia;
    private NoticiaRepository noticiaRepository;
    private ReporterRepository reporterRepository;
    private ParserHtml parserHtml;

    public MainCrawler(AtomicInteger numSeenImages, String seedUrl, CrawlController controller, Noticia noticia,
                       NoticiaRepository noticiaRepository, ParserHtml parserHtml, ReporterRepository reporterRepository) {
        this.numSeenImages = numSeenImages;
        this.seedUrl = seedUrl.toLowerCase();
        this.controller = controller;
        this.noticia = noticia;
        this.noticiaRepository = noticiaRepository;
        this.parserHtml = parserHtml;
        this.reporterRepository = reporterRepository;
        logger.info("Iniciando processo de crawling com a seed: {}", seedUrl);
    }

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        boolean shouldVisit = href.startsWith(seedUrl) && !IMAGE_EXTENSIONS.matcher(href).matches();
        logger.debug("Verificação de visita para URL '{}', seed '{}', resultado: {}", href, seedUrl, shouldVisit);
        return shouldVisit;
    }

    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        double pageSizeMB = page.getContentData().length / (1024.0 * 1024.0); // Tamanho em MB

        logger.info("Visitando URL: {} (Tamanho: {:.2f} MB)", url, pageSizeMB);

        // Verifica o tamanho do conteúdo antes de processar
        if (page.getContentData().length > MAX_HTML_SIZE) {
            logger.warn("Ignorando a URL {} devido ao tamanho excessivo do conteúdo (acima de 10 MB).", url);
            return;
        }

        // Verifica se a URL já foi processada
        if (noticiaRepository.existsByUrl(url)) {
            logger.info("A URL {} já foi processada anteriormente, ignorando...", url);
            return; // Evita processamento duplicado
        }

        noticia.setUrl(url);

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String html = htmlParseData.getHtml();
            Set<WebURL> links = htmlParseData.getOutgoingUrls();

            logger.debug("Tamanho do HTML: {}", html.length());
            logger.debug("Número de links externos: {}", links.size());

            // Log das URLs encontradas na página atual
            for (WebURL link : links) {
                logger.info("URL encontrada: {}", link.getURL());
            }

            // Salva o HTML em um arquivo para processamento posterior
            String filePath = saveHtmlToFile(url, html, "./src/main/java/com/group/backend/crawler/dadosCrawler/");
            logger.info("HTML da URL {} salvo em {}", url, filePath);

            // Passa o arquivo para o ParserHtml processar com as tags e o portal atuais
            try {
                parserHtml.parseAndDeleteFile(Paths.get(filePath), noticia);
            } catch (Exception e) {
                logger.error("Erro ao parsear o arquivo da URL {}: {}", url, e.getMessage());
            }

            // Adiciona links a serem visitados na fila do crawler
            for (WebURL link : links) {
                String newUrl = link.getURL();
                if (shouldVisit(page, link)) {
                    controller.addSeed(newUrl);  // Atualize se necessário para `controller.addUrlToQueue(newUrl);`
                    logger.info("URL adicionada à fila para visita: {}", newUrl);
                } else {
                    logger.debug("URL ignorada: {}", newUrl);
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
        String fileName = url.replaceAll("[^a-zA-Z0-9]", "_") + ".html";
        String filePath = outputDir + fileName;

        try {
            File directory = new File(outputDir);
            if (!directory.exists() && !directory.mkdirs()) {
                logger.error("Erro ao criar o diretório: {}", outputDir);
                return null;
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write(html);
                logger.info("HTML salvo em {}", filePath);
            }

        } catch (IOException e) {
            logger.error("Erro ao salvar HTML em arquivo: {}", e.getMessage());
            return null;
        }

        return filePath;
    }
}
