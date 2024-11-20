package com.group.backend.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group.backend.domain.NoticiaRepository;
import com.group.backend.entity.Noticia;

@RestController
@RequestMapping("/noticias")
@CrossOrigin(origins = "*")
public class NoticiaController {

    private static Integer PAGE_LENGTH = 10;
    private final NoticiaRepository noticiaRepository;

    public NoticiaController(NoticiaRepository noticiaRepository) {
        this.noticiaRepository = noticiaRepository;
    }

    @GetMapping("listar/{pageIndex}")
    public ResponseEntity<List<Noticia>> listarNoticias(@PathVariable Integer pageIndex) {
        Pageable pageable = PageRequest.of(pageIndex, PAGE_LENGTH);
        Page<Noticia> noticiaPage = noticiaRepository.findAll(pageable);
        List<Noticia> noticiaList = noticiaPage.toList();
        for (Noticia noticia: noticiaList) {
            noticia.setNotiText(noticia.getNotiText().substring(0, 255).concat("..."));
        }
        return ResponseEntity.ok(noticiaList);
    }
    
    @GetMapping("total")
    public ResponseEntity<Long> getTotal() {
        return ResponseEntity.ok(noticiaRepository.count());
    }

}