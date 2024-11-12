package com.group.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group.backend.domain.NoticiaRepository;
import com.group.backend.entity.Noticia;
import com.group.backend.service.NoticiaPortalService;

@RequestMapping("/noticias")
@RestController
@CrossOrigin(origins = "*")
public class NoticiaController {
    private final NoticiaRepository noticiaRepository;
    private final NoticiaPortalService noticiaPortalService;

    public NoticiaController(NoticiaRepository noticiaRepository, NoticiaPortalService noticiaPortalService) {
        this.noticiaRepository = noticiaRepository;
        this.noticiaPortalService = noticiaPortalService; 
    }

    @GetMapping("listar")
    public ResponseEntity<List<Noticia>> listarNoticias() {
        List<Noticia> noticias = noticiaRepository.findAll();

        for(Noticia noticia : noticias) {
            System.out.println("Noticia: " + noticia.getNotiText() + ", ID: " + noticia.getNotiId());
        }
        return ResponseEntity.ok(noticias);
    }

}
