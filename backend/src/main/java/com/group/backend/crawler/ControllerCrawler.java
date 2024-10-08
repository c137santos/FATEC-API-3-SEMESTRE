package com.group.backend.crawler;

import java.util.concurrent.atomic.AtomicInteger;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class ControllerCrawler {
    public static void main(String[] args) throws Exception {
        CrawlConfig config = new CrawlConfig();

        // Set the folder where intermediate crawl data is stored.
        config.setCrawlStorageFolder("backend/src/main/java/com/group/backend/crawler/dadosCrawler/frontier/");

        // Configurações adicionais...
        config.setPolitenessDelay(1000);
        config.setMaxDepthOfCrawling(-1);
        config.setMaxPagesToFetch(1000);
        config.setIncludeBinaryContentInCrawling(false);
        config.setResumableCrawling(false);

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        robotstxtConfig.setEnabled(false);
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        // Adicionando seeds iniciais
        controller.addSeed("https://www.ics.uci.edu/");
        controller.addSeed("https://ics.uci.edu/faculty-staff-resources/");
        controller.addSeed("https://www.ics.uci.edu/~welling/");

        // Número de crawlers a serem utilizados
        int numberOfCrawlers = 2;

        AtomicInteger numSeenImages = new AtomicInteger();

        // Passando o controlador para o MainCrawler
        CrawlController.WebCrawlerFactory<MainCrawler> factory = () -> new MainCrawler(numSeenImages, controller);

        // Iniciando o crawl
        controller.start(factory, numberOfCrawlers);
    }
}
