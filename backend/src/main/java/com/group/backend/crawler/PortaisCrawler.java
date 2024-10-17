package com.group.backend.crawler;

import com.group.backend.domain.PortalRepository;
import com.group.backend.entity.Noticia;
import com.group.backend.entity.Portal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.event.ApplicationReadyEvent;

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

        // Obtém todos os portais para também pegar o id
        List<Portal> portals = portalRepository.findAll();

        for (Portal portal : portals) {
            String url = portal.getUrl();
            Long portalId = portal.getId();
            
            try {
                System.out.println("Iniciando crawl para: " + url);
                
                Noticia noticia = new Noticia();
                noticia.setUrl(url);
                noticia.setPorId(portalId.intValue());

                controllerCrawler.startCrawlWithSeed(url, noticia);

                String folderPath = "backend/src/main/java/com/group/backend/crawler/dadosCrawler";
                parserHtml.parseAllFilesInFolder(folderPath, noticia);

            } catch (Exception e) {
                System.err.println("Erro ao iniciar o crawler para a URL: " + url);
                e.printStackTrace();
            }
        }
    }
}
