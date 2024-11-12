package com.group.backend.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group.backend.domain.NoticiaRepository;
import com.group.backend.domain.PortalRepository;
import com.group.backend.entity.Noticia;

@Service
public class NoticiaPortalService {
    private final PortalRepository portalRepository;
    private final NoticiaRepository noticiaRepository;    
    
    @Autowired
    public NoticiaPortalService(PortalRepository portalRepository, NoticiaRepository noticiaRepository) {
        this.portalRepository = portalRepository;
        this.noticiaRepository = noticiaRepository;
    }

    public List<Map<String, Object>> listarNoticiasComPortais() {
        List<Noticia> noticias = noticiaRepository.findAll();
        List<Map<String, Object>> resposta = new ArrayList<>();

        for (Noticia noticia : noticias) {
            Map<String, Object> notiMap = new HashMap<>();
            notiMap.put("id", noticia.getNotiId());
            notiMap.put("texto", noticia.getNotiText());
            notiMap.put("data", noticia.getNotiData());
          //  HashMap<Long, String> nomePortais = listarNoticiasComPortais(noticia.getNotiId());
            notiMap.put("portais", nomePortais);

            resposta.add(notiMap);   
        }
        return resposta;
    }

  //  public HashMap<Long, String> listarReporterComNoticia() {

    // }
}