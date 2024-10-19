package com.group.backend.controller;

import com.group.backend.entity.Regionalismo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.group.backend.domain.RegionalismoRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/regionalismos")
@CrossOrigin(origins = "http://localhost:8080")
public class RegionalismoController {

    @Autowired
    private RegionalismoRepository regionalismoRepository;

    @GetMapping("/listar")
    public List<Regionalismo> listarRegionalismos() {
        return regionalismoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Regionalismo> buscarPorId(@PathVariable Long id) {
        Optional<Regionalismo> regionalismo = regionalismoRepository.findById(id);
        if (regionalismo.isPresent()) {
            return ResponseEntity.ok(regionalismo.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/criar")
    public ResponseEntity<Regionalismo> criarRegionalismo(@RequestBody Regionalismo regionalismo) {
        Regionalismo novoRegionalismo = regionalismoRepository.save(regionalismo);
        return ResponseEntity.ok(novoRegionalismo);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Regionalismo> editarRegionalismo(@PathVariable Long id, @RequestBody Regionalismo dadosAtualizados) {
        Optional<Regionalismo> regionalismoExistente = regionalismoRepository.findById(id);

        if (regionalismoExistente.isPresent()) {
            Regionalismo regionalismo = regionalismoExistente.get();
            regionalismo.setNome(dadosAtualizados.getNome());
            regionalismo.setTagId(dadosAtualizados.getTagId());

            Regionalismo regionalismoAtualizado = regionalismoRepository.save(regionalismo);
            return ResponseEntity.ok(regionalismoAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarRegionalismo(@PathVariable Long id) {
        Optional<Regionalismo> regionalismoExistente = regionalismoRepository.findById(id);
        if (regionalismoExistente.isPresent()) {
            regionalismoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Regionalismo> save(@RequestBody Regionalismo regionalismo) {
        Regionalismo newRegionalismo = regionalismoRepository.save(regionalismo);
        return ResponseEntity.ok(newRegionalismo);
    }
}
