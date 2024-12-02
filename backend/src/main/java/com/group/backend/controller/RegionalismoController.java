package com.group.backend.controller;


import java.util.List;
import java.util.Optional; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group.backend.domain.RegionalismoRepository;
import com.group.backend.entity.Regionalismo;

@RestController
@RequestMapping("/regionalismos")
@CrossOrigin(origins = "*") 
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
        return regionalismo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Regionalismo> criarRegionalismo(@RequestBody Regionalismo regionalismo) {
        Regionalismo novoRegionalismo = regionalismoRepository.save(regionalismo);
        return ResponseEntity.ok(novoRegionalismo);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<String> atualizarRegionalismo(@PathVariable Long id, @RequestBody Regionalismo regionalismoAtualizado) {
        // Busca o regionalismo pelo ID fornecido
        Optional<Regionalismo> regionalismoExistente = regionalismoRepository.findById(id);

        if (regionalismoExistente.isPresent()) {
            // Se o regionalismo existe, atualiza seus dados
            Regionalismo regionalismo = regionalismoExistente.get();
            regionalismo.setNome(regionalismoAtualizado.getNome());
            regionalismo.setTagId(regionalismoAtualizado.getTagId());
            
            // Salva o regionalismo atualizado no repositório
            regionalismoRepository.save(regionalismo);
            return new ResponseEntity<>("Regionalismo atualizado com sucesso.", HttpStatus.OK);
        } else {
            // Se o regionalismo não for encontrado, retorna erro 404
            return new ResponseEntity<>("Regionalismo não encontrado.", HttpStatus.NOT_FOUND);
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
}
