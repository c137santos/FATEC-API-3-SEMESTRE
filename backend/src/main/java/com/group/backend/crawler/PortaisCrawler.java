package com.group.backend.crawler;

import com.group.backend.domain.PortalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PortaisCrawler implements ApplicationListener<ApplicationReadyEvent> {

    private final PortalRepository portalRepository;

    @Autowired
    public PortaisCrawler(PortalRepository portalRepository) {
        this.portalRepository = portalRepository;
    }

    // Este método será chamado automaticamente quando a aplicação estiver pronta
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ControllerCrawler controllerCrawler = new ControllerCrawler();

        List<String> urls = portalRepository.findAllUrlsBy();

        for (String url : urls) {
            try {
                System.out.println("Iniciando crawl para: " + url);
                controllerCrawler.startCrawlWithSeed(url);
            } catch (Exception e) {
                System.err.println("Erro ao iniciar o crawler para a URL: " + url);
                e.printStackTrace();
            }
        }
    }
}
