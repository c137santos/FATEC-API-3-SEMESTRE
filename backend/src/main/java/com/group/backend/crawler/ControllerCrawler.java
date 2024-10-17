package com.group.backend.crawler;

import java.util.concurrent.atomic.AtomicInteger;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.group.backend.entity.Noticia;
 

public class ControllerCrawler {

    private static final Logger logger = LoggerFactory.getLogger(ControllerCrawler.class);
    private static final String CRAWL_STORAGE_FOLDER = "./dadosCrawler";
    private static final int POLITENESS_DELAY = 1000; // 1 segundo
    private static final int MAX_PAGES_TO_FETCH = 1000; // Número máximo de páginas a serem buscadas
    private static final boolean INCLUDE_BINARY_CONTENT = false; // Não incluir conteúdo binário no crawling

    public void startCrawlWithSeed(String seedUrl, Noticia noticia) {
        try {
            CrawlConfig config = new CrawlConfig();
            config.setCrawlStorageFolder(CRAWL_STORAGE_FOLDER);
            config.setPolitenessDelay(POLITENESS_DELAY);
            config.setMaxDepthOfCrawling(-1); // Sem limite de profundidade
            config.setMaxPagesToFetch(MAX_PAGES_TO_FETCH);
            config.setIncludeBinaryContentInCrawling(INCLUDE_BINARY_CONTENT);
            config.setResumableCrawling(false);

            PageFetcher pageFetcher = new PageFetcher(config);
            RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
            robotstxtConfig.setEnabled(false); // Desativar o respeito ao arquivo robots.txt
            RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);

            CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

            // Adiciona a URL semente
            controller.addSeed(seedUrl);

            int numberOfCrawlers = 3;
            AtomicInteger numSeenImages = new AtomicInteger();

            // Passa o objeto Noticia para o MainCrawler
            CrawlController.WebCrawlerFactory<MainCrawler> factory = () -> new MainCrawler(numSeenImages, seedUrl, controller, noticia);

            // Inicia o crawling
            controller.start(factory, numberOfCrawlers);
        } catch (Exception e) {
            logger.error("Erro ao iniciar o crawling: {}", e.getMessage());
        }
    }
}
