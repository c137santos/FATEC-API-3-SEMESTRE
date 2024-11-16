package com.group.backend.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group.backend.domain.NoticiaRepository;
import com.group.backend.domain.PortalRepository;
import com.group.backend.domain.ReporterRepository;
import com.group.backend.entity.Noticia;
import com.group.backend.entity.Portal;
import com.group.backend.entity.Reporter;

@Service
public class NoticiaPortalService {
    private final PortalRepository portalRepository;
    private final NoticiaRepository noticiaRepository;    
    private ReporterRepository reporterRepository;
    
    @Autowired
    public NoticiaPortalService(PortalRepository portalRepository, NoticiaRepository noticiaRepository, ReporterRepository reporterRepository) {
        this.portalRepository = portalRepository;
        this.noticiaRepository = noticiaRepository;
        this.reporterRepository = reporterRepository;
    }

    
    public List<Map<String, Object>> listarNoticiasComPortais() {
        List<Noticia> noticias = noticiaRepository.findAll();
        List<Map<String, Object>> resposta = new ArrayList<>();

        if(noticias == null || noticias.isEmpty()) {
            return resposta;
        }
        
        for (Noticia noticia : noticias) {
            Map<String, Object> notiMap = new HashMap<>();
            notiMap.put("id", noticia.getNotiId());
            notiMap.put("url", noticia.getUrl());
            notiMap.put("texto", noticia.getNotiText());
            notiMap.put("data", noticia.getNotiData());
            HashMap<Long, String> nomePortais = listarNoticiaPortal(noticia.getNotiId());
            notiMap.put("portais", nomePortais);

            resposta.add(notiMap);   
        }
        return resposta;
    }
    
    private HashMap<Long, String> listarNoticiaPortal(Long notiId) {
        HashMap<Long, String> portais = new HashMap<>();
        List<Portal> portaisRelacionados = portalRepository.findByNoticiaId(notiId);
        for(Portal portal : portaisRelacionados) {
            portais.put(portal.getId(), portal.getNome());
        }
        return portais;
    }
    
    public HashMap<Long, String> listarReporterComNoticia(Long notiId) {
        HashMap<Long, String> nomeReport = new HashMap<>();
        List<Reporter> reporters = reporterRepository.findByPortalId(notiId);
        for (Reporter reporter : reporters) {
          nomeReport.put(reporter.getRepId(), reporter.getNome());
        }
        return nomeReport;
    }
}
