package com.group.backend.controller;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group.backend.domain.DadosCadastroPortal;
import com.group.backend.domain.PortalRepository;
import com.group.backend.domain.TagPortalRepository;
import com.group.backend.entity.Portal;
import com.group.backend.service.TagService;

import jakarta.transaction.Transactional;


@RequestMapping("/portais")
@RestController
@CrossOrigin(origins = "*")
public class PortalController {
    private final PortalRepository portalRepository;

    public PortalController(PortalRepository portalRepository, TagPortalRepository tagPortalRepository) {
        this.portalRepository = portalRepository;
    }

    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity<Portal> cadastrarPortal(@RequestBody DadosCadastroPortal dados) {
        Portal novoPortal = new Portal();
        novoPortal.setNome(dados.portalNome());
        novoPortal.setUrl(dados.portalUrl());
        novoPortal.setData(LocalDate.now());
        novoPortal.setFrequencia(dados.portalFrequencia());
        Portal portalSalvo = portalRepository.save(novoPortal);
        
        TagService ts = new TagService();
        ts.cadastrarTagPortal(dados.portalId(), dados.tagId());
        
        return ResponseEntity.ok(portalSalvo);
    }
}
    

