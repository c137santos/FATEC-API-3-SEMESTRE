package com.group.backend.controller;

import com.group.backend.entity.Portal;
import com.group.backend.domain.PortalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/portais")
public class PortalController {

    @Autowired
    private PortalRepository portalRepository;

    @PutMapping("/editar/{id}")
    public ResponseEntity<Portal> editarPortal(@PathVariable Long id, @RequestBody Portal portalAtualizado) {
        return portalRepository.findById(id)
                .map(portal -> {
                    portal.setNome(portalAtualizado.getNome());
                    portal.setUrl(portalAtualizado.getUrl());
                    portal.setFrequencia(portalAtualizado.getFrequencia());
                    portal.setData(LocalDate.now()); // Atualiza a data de criação
                    portalRepository.save(portal);
                    return ResponseEntity.ok(portal);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
