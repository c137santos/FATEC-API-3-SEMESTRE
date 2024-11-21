package com.group.backend.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group.backend.domain.NoticiaRepository;
import com.group.backend.entity.Noticia;
import com.group.backend.service.FilterCriteria;
import com.group.backend.service.FilterService;

@RestController
@RequestMapping("/noticias")
@CrossOrigin(origins = "*")
public class NoticiaController {

    private static final int PAGE_LENGTH = 10;
    private final NoticiaRepository noticiaRepository;
    private final FilterService filterService;

    public NoticiaController(NoticiaRepository noticiaRepository, FilterService filterService) {
        this.noticiaRepository = noticiaRepository;
        this.filterService = filterService;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Noticia>> listarNoticias(
        @RequestParam(required = false) String tag,
        @RequestParam(required = false) String portal,
        @RequestParam(defaultValue = "0") Integer pageIndex
    ) {
        Pageable pageable = PageRequest.of(pageIndex, PAGE_LENGTH);

        FilterCriteria criteria = filterService.processFilters(tag, portal);

        Page<Noticia> noticiaPage;
        if (criteria.getTags() == null && criteria.getPortals() == null) {
            noticiaPage = noticiaRepository.findAll(pageable);
        } else {
            noticiaPage = noticiaRepository.findByTagsAndPortals(criteria.getTags(), criteria.getPortals(), pageable);
        }

        List<Noticia> noticiaList = noticiaPage.toList();

        noticiaList.forEach(noticia -> {
            if (noticia.getNotiText() != null) {
                noticia.setNotiText(new String(noticia.getNotiText()).substring(0, 255).concat("..."));
            }
        });

        return ResponseEntity.ok(noticiaList);
    }

    @GetMapping("/total")
    public ResponseEntity<Long> getTotal(
        @RequestParam(required = false) String tag,
        @RequestParam(required = false) String portal
    ) {
        FilterCriteria criteria = filterService.processFilters(tag, portal);

        long total;
        if (criteria.getTags() == null && criteria.getPortals() == null) {
            total = noticiaRepository.count();
        } else {
            total = noticiaRepository.findByTagsAndPortals(criteria.getTags(), criteria.getPortals(), Pageable.unpaged())
                    .getTotalElements();
        }

        return ResponseEntity.ok(total);
    }
}
