package com.group.backend.crawler;

import com.group.backend.domain.NoticiaRepository;
import com.group.backend.domain.PortalRepository;
import com.group.backend.domain.ReporterRepository;
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
    private final NoticiaRepository noticiaRepository;
    private final ReporterRepository reporterRepository;
    private final ParserHtml parserHtml;

    @Autowired
    public PortaisCrawler(PortalRepository portalRepository, NoticiaRepository noticiaRepository, ParserHtml parserHtml, ReporterRepository reporterRepository) {
        this.portalRepository = portalRepository;
        this.noticiaRepository = noticiaRepository;
        this.parserHtml = parserHtml;
        this.reporterRepository = reporterRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void startCrawlForAllPortals() {
        ControllerCrawler controllerCrawler = new ControllerCrawler(noticiaRepository, reporterRepository);

        List<Portal> portals = portalRepository.findAll();

        if (portals.isEmpty()) {
            System.out.println("Não existem portais disponíveis para crawling.");
            return; // Retorna para não executar o loop
        }

        for (Portal portal : portals) {
            String url = portal.getUrl();
            Long portalId = portal.getId();

            try {
                System.out.println("Iniciando crawl para: " + url);
                
                Noticia noticia = new Noticia();
                noticia.setUrl(url);
                noticia.setPorId(portalId.intValue());

                controllerCrawler.startCrawlWithSeed(url, noticia, parserHtml, reporterRepository);


            } catch (Exception e) {
                System.out.println("Erro ao iniciar o crawl para " + url + ": " + e.getMessage());
            }
        }
    }
}
