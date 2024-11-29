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

    // Endpoint com filtros
    @GetMapping("/listar")
    public ResponseEntity<List<Noticia>> listarNoticiasComFiltros(
        @RequestParam(required = false) String tag,
        @RequestParam(required = false) String portal,
        @RequestParam(required = false) String reporter,
        @RequestParam(required = false) String startDate,
        @RequestParam(required = false) String endDate,
        @RequestParam(required = false) String keyword,
        @RequestParam(defaultValue = "0") Integer pageIndex
    ) {
        Pageable pageable = PageRequest.of(pageIndex, PAGE_LENGTH);

        // Processando filtros
        FilterCriteria criteria = filterService.processFilters(tag, portal, reporter, startDate, endDate, keyword);

        // Aplicando filtros
        Page<Noticia> noticiaPage = noticiaRepository.findByFilters(
            (criteria.getTags() == null || criteria.getTags().isEmpty()),
            criteria.getTags(),
            (criteria.getPortals() == null || criteria.getPortals().isEmpty()),
            criteria.getPortals(),
            (criteria.getReporters() == null || criteria.getReporters().isEmpty()),
            criteria.getReporters(),
            (criteria.getStartDate() == null),
            criteria.getStartDate(),
            (criteria.getEndDate() == null),
            criteria.getEndDate(),
            (criteria.getKeyword() == null || criteria.getKeyword().isEmpty()),
            criteria.getKeyword(),
            pageable
        );

        // Retornando a lista sem modificar o texto original
        return ResponseEntity.ok(noticiaPage.toList());
    }

    // Endpoint sem filtros
    @GetMapping("/listar/{pageIndex}")
    public ResponseEntity<List<Noticia>> listarNoticiasPaginado(@PathVariable Integer pageIndex) {
        Pageable pageable = PageRequest.of(pageIndex, PAGE_LENGTH);
        Page<Noticia> noticiaPage = noticiaRepository.findAll(pageable);

        // Retornando a lista sem modificar o texto original
        return ResponseEntity.ok(noticiaPage.toList());
    }

    @GetMapping("/total")
    public ResponseEntity<Long> getTotalNoticias(
        @RequestParam(required = false) String tag,
        @RequestParam(required = false) String portal,
        @RequestParam(required = false) String reporter,
        @RequestParam(required = false) String startDate,
        @RequestParam(required = false) String endDate,
        @RequestParam(required = false) String keyword
    ) {
        // Processando filtros
        FilterCriteria criteria = filterService.processFilters(tag, portal, reporter, startDate, endDate, keyword);

        // Calculando o total com filtros
        long total = noticiaRepository.findByFilters(
            (criteria.getTags() == null || criteria.getTags().isEmpty()),
            criteria.getTags(),
            (criteria.getPortals() == null || criteria.getPortals().isEmpty()),
            criteria.getPortals(),
            (criteria.getReporters() == null || criteria.getReporters().isEmpty()),
            criteria.getReporters(),
            (criteria.getStartDate() == null),
            criteria.getStartDate(),
            (criteria.getEndDate() == null),
            criteria.getEndDate(),
            (criteria.getKeyword() == null || criteria.getKeyword().isEmpty()),
            criteria.getKeyword(),
            Pageable.unpaged()
        ).getTotalElements();

        return ResponseEntity.ok(total);
    }
}
