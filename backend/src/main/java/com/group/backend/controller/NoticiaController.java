package com.group.backend.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group.backend.domain.NoticiaRepository;
import com.group.backend.entity.Noticia;

@RestController
@RequestMapping("/noticias")
@CrossOrigin(origins = "*")
public class NoticiaController {

    private static final Integer PAGE_LENGTH = 10;
    private final NoticiaRepository noticiaRepository;

    public NoticiaController(NoticiaRepository noticiaRepository) {
        this.noticiaRepository = noticiaRepository;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Noticia>> listarNoticias(
        @RequestParam(required = false) String tag,
        @RequestParam(defaultValue = "0") Integer pageIndex
    ) {
        Pageable pageable = PageRequest.of(pageIndex, PAGE_LENGTH);

        // Busca as notícias filtradas por tag
        Page<Noticia> noticiaPage = noticiaRepository.findByTag(tag, pageable);
        List<Noticia> noticiaList = noticiaPage.toList();

        // Trunca o texto para evitar retornos excessivamente grandes
        for (Noticia noticia : noticiaList) {
            if (noticia.getNotiText() != null) {
                noticia.setNotiText(new String(noticia.getNotiText()).substring(0, 255).concat("..."));
            }
        }

        return ResponseEntity.ok(noticiaList);
    }

    @GetMapping("/total")
    public ResponseEntity<Long> getTotal(
        @RequestParam(required = false) String tag
    ) {
        long total = noticiaRepository.findByTag(tag, Pageable.unpaged()).getTotalElements();
        return ResponseEntity.ok(total);
    }
}
