package com.group.backend.crawler;

import com.group.backend.domain.NoticiaRepository;
import com.group.backend.domain.PortalRepository;
import com.group.backend.domain.ReporterRepository;
import com.group.backend.entity.Noticia;
import com.group.backend.entity.Portal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PortaisCrawler {

    private final PortalRepository portalRepository;
    private final NoticiaRepository noticiaRepository;
    private final ReporterRepository reporterRepository;
    private final HtmlParserService htmlParserService; // Usando o serviço modularizado

    @Autowired
    public PortaisCrawler(
            PortalRepository portalRepository,
            NoticiaRepository noticiaRepository,
            HtmlParserService htmlParserService,
            ReporterRepository reporterRepository) {
        this.portalRepository = portalRepository;
        this.noticiaRepository = noticiaRepository;
        this.htmlParserService = htmlParserService;
        this.reporterRepository = reporterRepository;
    }

    public void startCrawlForFrequency(String frequencia) {
        ControllerCrawler controllerCrawler = new ControllerCrawler(
                noticiaRepository, reporterRepository, portalRepository, htmlParserService
        );

        List<Portal> portals = portalRepository.findByFrequenciaAndActiveTags(frequencia);

        if (portals.isEmpty()) {
            System.out.println("Não existem portais disponíveis para crawling com a frequência: " + frequencia);
            return; // Retorna para não executar o loop
        }

        for (Portal portal : portals) {
            String url = normalizeUrl(portal.getUrl());
            Long portalId = portal.getId();

            try {
                System.out.println("Iniciando crawl para: " + url + " com a frequência: " + frequencia);

                Noticia noticia = new Noticia();
                noticia.setUrl(url);
                noticia.setPorId(portalId.longValue());

                controllerCrawler.startCrawlWithSeed(new String[]{url}, noticia);

            } catch (Exception e) {
                System.out.println("Erro ao iniciar o crawl para " + url + ": " + e.getMessage());
            }
        }
    }

    private String normalizeUrl(String url) {
        if (url == null) return null;
        url = url.toLowerCase().trim();
        if (url.startsWith("www.")) {
            url = url.substring(4);
        }
        if (!url.startsWith("http")) {
            url = "https://" + url; // Adiciona "https://" se não houver protocolo
        }
        return url;
    }
}
