package com.group.backend.crawler;

import com.group.backend.domain.PortalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PortaisCrawler {

    private final PortalRepository portalRepository;
    private final ParserHtml parserHtml;

    @Autowired
    public PortaisCrawler(PortalRepository portalRepository, ParserHtml parserHtml) {
        this.portalRepository = portalRepository;
        this.parserHtml = parserHtml;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void startCrawlForAllPortals() {
        ControllerCrawler controllerCrawler = new ControllerCrawler();

        List<String> urls = portalRepository.findAllUrlsBy();

        for (String url : urls) {
            try {
                System.out.println("Iniciando crawl para: " + url);
                controllerCrawler.startCrawlWithSeed(url);

                // Após o crawling, chamar o método de parse
                String folderPath = "backend/src/main/java/com/group/backend/crawler/dadosCrawler";
                parserHtml.parseAllFilesInFolder(folderPath);

            } catch (Exception e) {
                System.err.println("Erro ao iniciar o crawler para a URL: " + url);
                e.printStackTrace();
            }
        }
    }
}
