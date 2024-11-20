package com.group.backend.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group.backend.domain.NoticiaRepository;
import com.group.backend.entity.Noticia;

@Service
public class NoticiaPortalService {
    private final NoticiaRepository noticiaRepository;

    @Autowired
    public NoticiaPortalService(NoticiaRepository noticiaRepository) {
        this.noticiaRepository = noticiaRepository;
    }

    public List<Map<String, Object>> getNoticiaComPortal() {
        List<Noticia> noticias = noticiaRepository.findAll();
        List<Map<String, Object>> resposta = new ArrayList<>();

        for (Noticia noticia : noticias) {
            Map<String, Object> notiMap = new HashMap<>();
            notiMap.put("id", noticia.getNotiId());
            notiMap.put("url", noticia.getUrl());
            notiMap.put("texto", noticia.getNotiText());
            notiMap.put("data", noticia.getNotiData());
        
            resposta.add(notiMap);
        }
        return resposta;
    }
}

