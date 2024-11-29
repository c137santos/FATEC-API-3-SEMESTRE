package com.group.backend.service;

import java.util.Optional;

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

    public Optional<Noticia> noticiaSelecionada(Long id) {
        Optional<Noticia> nt = noticiaRepository.findById(id);
            return nt;
    }

}

