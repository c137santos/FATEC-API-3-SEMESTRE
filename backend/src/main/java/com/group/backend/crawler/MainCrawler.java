package com.group.backend.crawler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashSet;
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
import com.group.backend.entity.Portal;
import com.group.backend.domain.NoticiaRepository;
import com.group.backend.domain.PortalRepository;
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
    private PortalRepository portalRepository;
    private ReporterRepository reporterRepository;
    private HtmlParserService htmlParserService;
    private static Set<String> portaisVisitados = new HashSet<>();

    public MainCrawler(AtomicInteger numSeenImages, String seedUrl, CrawlController controller, Noticia noticia,
                       NoticiaRepository noticiaRepository, PortalRepository portalRepository,
                       HtmlParserService htmlParserService, ReporterRepository reporterRepository) {
        this.numSeenImages = numSeenImages;
        this.seedUrl = normalizeUrl(seedUrl);
        this.controller = controller;
        this.noticia = noticia;
        this.noticiaRepository = noticiaRepository;
        this.portalRepository = portalRepository;
        this.htmlParserService = htmlParserService;
        this.reporterRepository = reporterRepository;
        logger.info("Iniciando processo de crawling com a seed: {}", seedUrl);
    }

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = normalizeUrl(url.getURL());
        String normalizedSeedUrl = normalizeUrl(seedUrl);

        if (IMAGE_EXTENSIONS.matcher(href).matches()) {
            logger.debug("Ignorando URL com extensão irrelevante: {}", href);
            return false;
        }

        boolean shouldVisit = href.startsWith(normalizedSeedUrl);
        logger.debug("Verificação de visita: href = {}, seedUrl = {}, resultado = {}", href, normalizedSeedUrl, shouldVisit);

        return shouldVisit;
    }

    private String normalizeUrl(String url) {
        if (url == null) return null;

        // Remove 'http://', 'https://', e 'www.' para normalização
        return url.replaceAll("^(https?://)?(www\\.)?", "").toLowerCase().trim();
    }

    @Override
    public void visit(Page page) {
        String url = normalizeUrl(page.getWebURL().getURL());
        double pageSizeMB = page.getContentData().length / (1024.0 * 1024.0); // Tamanho em MB
        logger.info("Visitando URL: {} (Tamanho: {:.2f} MB)", url, pageSizeMB);

        if (page.getContentData().length > MAX_HTML_SIZE) {
            logger.warn("Ignorando a URL {} devido ao tamanho excessivo do conteúdo (acima de 10 MB).", url);
            return;
        }

        if (noticiaRepository.existsByUrl(url)) {
            logger.info("A URL {} já foi processada anteriormente, ignorando...", url);
            return;
        }

        noticia.setUrl(url);

        // Usa o método findByNormalizedUrl para buscar o portal
        Portal portal = portalRepository.findByNormalizedUrl(seedUrl);
        if (portal == null) {
            logger.error("Portal não encontrado para a seed: {}", seedUrl);
            return;
        }
        noticia.setPortal(portal);
        logger.info("Portal associado à notícia: {}", portal.getNome());

        portaisVisitados.add(seedUrl);

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String html = htmlParseData.getHtml();
            Set<WebURL> links = htmlParseData.getOutgoingUrls();

            logger.debug("Tamanho do HTML: {}", html.length());
            logger.debug("Número de links externos: {}", links.size());

            String filePath = saveHtmlToFile(url, html, "./src/main/java/com/group/backend/crawler/dadosCrawler/");
            logger.info("HTML da URL {} salvo em {}", url, filePath);

            try {
                htmlParserService.parseAndDeleteFile(Paths.get(filePath), noticia);
            } catch (Exception e) {
                logger.error("Erro ao parsear o arquivo da URL {}: {}", url, e.getMessage());
            }

            for (WebURL link : links) {
                String newUrl = link.getURL();
                if (shouldVisit(page, link)) {
                    controller.addSeed(newUrl);
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

    @Override
    public void onBeforeExit() {
        if (!portaisVisitados.isEmpty()) {
            logger.info("Portais com seeds vasculhados: {}", String.join(", ", portaisVisitados));
            System.out.println("Portais com seeds vasculhados: " + String.join(", ", portaisVisitados));
        } else {
            logger.info("Nenhum portal foi completamente vasculhado.");
            System.out.println("Nenhum portal foi completamente vasculhado.");
        }
    }
}
