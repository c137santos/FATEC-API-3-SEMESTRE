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
import com.group.backend.entity.Portal;
import com.group.backend.service.TagPortalService;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group.backend.domain.PortalRepository;
import com.group.backend.dto.PortalTagDTO;
import com.group.backend.entity.Portal;

@RestController
@RequestMapping("/portais")
@CrossOrigin(origins = "*")
public class PortalController {

    private final PortalRepository portalRepository;
    private final TagPortalService tagPortalService;

    public PortalController(PortalRepository portalRepository, TagPortalService tagPortalService) {
        this.portalRepository = portalRepository;
        this.tagPortalService = tagPortalService;
    }

    @PostMapping("cadastrar")
    @Transactional
    public ResponseEntity<Portal> cadastrarPortal(@RequestBody DadosCadastroPortal dados) {
        Portal novoPortal = new Portal();
        novoPortal.setNome(dados.portalNome());
        novoPortal.setUrl(dados.portalUrl());
        novoPortal.setData(LocalDate.now());
        novoPortal.setFrequencia(dados.portalFrequencia());
        Portal portalSalvo = portalRepository.save(novoPortal);
        
        tagPortalService.cadastrarTagPortal(dados.tagId(), portalSalvo);
        
        return ResponseEntity.ok(portalSalvo);
    }


    @GetMapping("/listar")
    public ResponseEntity<List<PortalTagDTO>> listarPortais() {
        // Buscar todos os portais e converter para DTO, incluindo o campo ativo e a data
        List<PortalTagDTO> portalTagDTOs = portalRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(portalTagDTOs);
    }

    // Método auxiliar para converter Portal em PortalTagDTO
    private PortalTagDTO convertToDTO(Portal portal) {
        List<String> tagNomes = portal.getTagPortals().stream()
                .map(tagPortal -> tagPortal.getTag().getTagNome())
                .collect(Collectors.toList());

        return new PortalTagDTO(
                portal.getId(),
                portal.getNome(),
                portal.getUrl(),
                portal.getFrequencia(),
                tagNomes,
                portal.isAtivo(),
                portal.getData() // Adicionar a data aqui
        );
    }
}
