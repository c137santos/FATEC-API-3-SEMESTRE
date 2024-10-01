package com.group.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group.backend.domain.DadosCadastroPortal;
import com.group.backend.domain.PortalRepository;
import com.group.backend.entity.Portal;

import jakarta.transaction.Transactional;


@RequestMapping("/portais")
@RestController
@CrossOrigin(origins = "*")
public class PortalController {
    private final PortalRepository portalRepository;

    public PortalController(PortalRepository portalRepository) {
        this.portalRepository = portalRepository;
    }

    @PostMapping("cadadstrar")
    @Transactional
    public ResponseEntity<Portal> cadastrarPortal(@RequestBody DadosCadastroPortal dados) {
    
        Portal novoPortal = new Portal();
        novoPortal.setNome(dados.portalNome());
        novoPortal.setUrl(dados.portalUrl());
        novoPortal.setFrequencia(dados.portalFrequencia());
        novoPortal.setData(dados.portalData());
        
        Portal portalSalvo = portalRepository.save(novoPortal);
        return ResponseEntity.ok(portalSalvo);
    }
    

}

