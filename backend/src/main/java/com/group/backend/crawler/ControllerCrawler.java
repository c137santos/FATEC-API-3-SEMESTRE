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
import com.group.backend.domain.NoticiaRepository;
import com.group.backend.domain.ReporterRepository;

public class ControllerCrawler {

    private static final Logger logger = LoggerFactory.getLogger(ControllerCrawler.class);
    private static final String CRAWL_STORAGE_FOLDER = "./dadosCrawler";
    private static final int POLITENESS_DELAY = 1000; // 1 segundo
    private static final int MAX_PAGES_TO_FETCH = 100; // Número máximo de páginas a serem buscadas
    private static final boolean INCLUDE_BINARY_CONTENT = false; // Não incluir conteúdo binário no crawling

    private NoticiaRepository noticiaRepository;
    private ReporterRepository reporterRepository;

    public ControllerCrawler(NoticiaRepository noticiaRepository, ReporterRepository reporterRepository) {
        this.noticiaRepository = noticiaRepository;
        this.reporterRepository = reporterRepository;
    }

    public void startCrawlWithSeed(String seedUrl, Noticia noticia, ParserHtml parserHtml, ReporterRepository reporterRepository) {
        try {
            logger.info("Iniciando processo de crawling com a seed: {}", seedUrl); // Log da seed
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

            int numberOfCrawlers = 1;
            AtomicInteger numSeenImages = new AtomicInteger();

            // Passa o objeto Noticia e o repositório para o MainCrawler
            CrawlController.WebCrawlerFactory<MainCrawler> factory = () -> new MainCrawler(numSeenImages, seedUrl, controller, noticia, noticiaRepository, parserHtml, reporterRepository);

            // Inicia o crawling
            controller.start(factory, numberOfCrawlers);

            // Verifica o status do controlador e adia o shutdown até todas as URLs serem processadas
            while (!controller.isFinished()) {
                Thread.sleep(1000); // Aguarda 1 segundo antes de checar novamente
            }
            controller.shutdown();
            logger.info("Crawling finalizado e controller encerrado.");
            
        } catch (Exception e) {
            logger.error("Erro ao iniciar o crawling: {}", e.getMessage());
        }
    }
}
