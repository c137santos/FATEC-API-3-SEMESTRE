package com.group.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.group.backend.domain.NoticiaRepository;
import com.group.backend.entity.Noticia;
import com.group.backend.service.FilterCriteria;
import com.group.backend.service.FilterService;
import com.group.backend.service.NoticiaPortalService;

@RestController
@RequestMapping("/noticias")
@CrossOrigin(origins = "*")
public class NoticiaController {

    private static final int PAGE_LENGTH = 10;
    private final NoticiaRepository noticiaRepository;
    private final FilterService filterService;
    private final NoticiaPortalService noticiaPortalService;

    public NoticiaController(NoticiaRepository noticiaRepository, FilterService filterService, NoticiaPortalService noticiaPortalService) {
        this.noticiaRepository = noticiaRepository;
        this.filterService = filterService;
        this.noticiaPortalService = noticiaPortalService;
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

    // Endpoint sem filtros com encurtamento do texto
    @GetMapping("/listar/{pageIndex}")
    public ResponseEntity<List<Noticia>> listarNoticiasPaginado(@PathVariable Integer pageIndex) {
        Pageable pageable = PageRequest.of(pageIndex, PAGE_LENGTH);
        Page<Noticia> noticiaPage = noticiaRepository.findAll(pageable);
        List<Noticia> noticiaList = noticiaPage.toList();
        for (Noticia noticia : noticiaList) {
            String text = noticia.getNotiText();
            if (text != null && text.length() > 255) {
                noticia.setNotiText(text.substring(0, 255).concat("..."));
            }
        }
        return ResponseEntity.ok(noticiaList);
    }

    // Endpoint para obter uma notícia específica
    @GetMapping("{id}")
    public ResponseEntity<Noticia> noticiaEspecifica(@PathVariable Long id) {
        Optional<Noticia> dadosNoticiaEspecifica = noticiaPortalService.noticiaSelecionada(id);
        return dadosNoticiaEspecifica.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint para total de notícias sem filtros
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

    // Endpoint para total de notícias sem filtros
    @GetMapping("/totalSemFiltros")
    public ResponseEntity<Long> getTotal() {
        return ResponseEntity.ok(noticiaRepository.count());
    }
}
