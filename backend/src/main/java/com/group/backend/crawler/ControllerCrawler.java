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
import com.group.backend.domain.PortalRepository; // Import necessário para associar portais
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ControllerCrawler {

    private static final Logger logger = LoggerFactory.getLogger(ControllerCrawler.class);
    private static final String CRAWL_STORAGE_FOLDER = "./dadosCrawler";
    private static final int MAX_PAGES_TO_FETCH = 1000; // Número máximo de páginas a serem buscadas
    private static final boolean INCLUDE_BINARY_CONTENT = false; // Não incluir conteúdo binário no crawling
    private static final int NUMBER_OF_CRAWLERS = 100; // Número de threads para o crawling
    private static final int MIN_POLITENESS_DELAY = 500; // Delay mínimo em milissegundos
    private static final int MAX_POLITENESS_DELAY = 3000; // Delay máximo em milissegundos

    private final NoticiaRepository noticiaRepository;
    private final ReporterRepository reporterRepository;
    private final PortalRepository portalRepository; // Adicionado para buscar portais associados às seeds
    private final HtmlParserService htmlParserService;
    private final List<CrawlController> controllers = new ArrayList<>();

    public ControllerCrawler(NoticiaRepository noticiaRepository, ReporterRepository reporterRepository, PortalRepository portalRepository,
                             HtmlParserService htmlParserService) {
        this.noticiaRepository = noticiaRepository;
        this.reporterRepository = reporterRepository;
        this.portalRepository = portalRepository; // Inicializado
        this.htmlParserService = htmlParserService;
    }

    public void startCrawlWithSeed(String[] seedUrls, Noticia noticia) {
        try {
            logger.info("Iniciando configuração do crawling...");

            CrawlConfig config = new CrawlConfig();
            config.setCrawlStorageFolder(CRAWL_STORAGE_FOLDER);
            config.setPolitenessDelay(getRandomPolitenessDelay());
            config.setMaxDepthOfCrawling(-1); // Sem limite de profundidade
            config.setMaxPagesToFetch(MAX_PAGES_TO_FETCH);
            config.setIncludeBinaryContentInCrawling(INCLUDE_BINARY_CONTENT);
            config.setResumableCrawling(false);

            // Definir um User-Agent aleatório
            config.setUserAgentString(UserAgentProvider.getRandomUserAgent());

            PageFetcher pageFetcher = new PageFetcher(config);
            RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
            robotstxtConfig.setEnabled(false); // Desabilita a verificação de robots.txt
            RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);

            int numSeeds = seedUrls.length;
            logger.info("Número total de seeds recebidas: {}", numSeeds);
            int availableProcessors = Runtime.getRuntime().availableProcessors();
            int threads = Math.min(NUMBER_OF_CRAWLERS, availableProcessors * 2); // Ajuste baseado no número de núcleos de CPU

            for (int i = 0; i < threads; i++) {
                final int threadIndex = i; // Variável final para uso no lambda
                int start = threadIndex * (numSeeds / threads);
                int end = (threadIndex == threads - 1) ? numSeeds : (threadIndex + 1) * (numSeeds / threads);

                if (start >= end) {
                    logger.info("Nenhuma seed atribuída à thread {}", threadIndex + 1);
                    continue;
                }

                String[] seedsForCrawler = new String[end - start];
                for (int j = 0; j < seedsForCrawler.length; j++) {
                    seedsForCrawler[j] = normalizeUrl(seedUrls[start + j]);
                }
                logger.info("Seeds atribuídas à thread {}: {}", threadIndex + 1, String.join(", ", seedsForCrawler));

                CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
                controllers.add(controller);

                for (String seedUrl : seedsForCrawler) {
                    if (seedUrl != null && !seedUrl.isEmpty()) {
                        controller.addSeed(seedUrl);
                        logger.info("Seed adicionada para a thread {}: {}", threadIndex + 1, seedUrl);
                    } else {
                        logger.warn("Seed inválida detectada e ignorada na thread {}.", threadIndex + 1);
                    }
                }

                AtomicInteger numSeenImages = new AtomicInteger();
                CrawlController.WebCrawlerFactory<MainCrawler> factory = () -> {
                    logger.debug("Criando instância de MainCrawler para a thread {} com seed {}", threadIndex + 1, seedsForCrawler.length > 0 ? seedsForCrawler[0] : "N/A");
                    return new MainCrawler(numSeenImages, seedsForCrawler.length > 0 ? seedsForCrawler[0] : null, controller,
                            noticia, noticiaRepository, portalRepository, htmlParserService, reporterRepository); // Adicionado portalRepository
                };

                logger.info("Iniciando o processo de crawling com 1 thread para a thread {} com seeds: {}", threadIndex + 1, String.join(", ", seedsForCrawler));
                controller.startNonBlocking(factory, 1);
            }

            logger.info("Monitorando a conclusão de todas as threads de crawling...");
            for (CrawlController controller : controllers) {
                while (!controller.isFinished()) {
                    logger.info("Aguardando a conclusão do crawling em uma das threads...");
                    Thread.sleep(5000); // Intervalo de verificação em milissegundos
                }
                logger.info("Crawling concluído para uma das threads.");
            }

            logger.info("Crawling finalizado para todas as seeds.");
            System.out.println("Crawling finalizado para todas as seeds.");

        } catch (Exception e) {
            logger.error("Erro ao iniciar o crawling: ", e);
        }
    }

    private String normalizeUrl(String url) {
        if (url == null) return null;
        url = url.toLowerCase().trim();
        if (url.startsWith("www.")) {
            url = url.substring(4);
        }
        if (!url.startsWith("http")) {
            url = "http://" + url; // Adiciona "http://" se não houver protocolo
        }
        return url;
    }

    private int getRandomPolitenessDelay() {
        return ThreadLocalRandom.current().nextInt(MIN_POLITENESS_DELAY, MAX_POLITENESS_DELAY + 1);
    }
}
