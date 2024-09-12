package com.group.backend.controller;

import com.group.backend.domain.Tags.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.util.List;

@RequestMapping("/tags")
@RestController
public class TagController {

    @Autowired
    private TagRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<Tag> cadastrarTag(@RequestBody @Valid DadosCadastroTag dados, UriComponentsBuilder uriBuilder) {
        var tag = new Tag(dados);
        repository.save(tag);

        var uri = uriBuilder.path("/tags/{id}").buildAndExpand(tag.getId()).toUri();

        return ResponseEntity.created(uri).body(tag);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<Tag> atualizarTag(@RequestBody @Valid DadosAtualizacaoTag dados) {
        var tagOptional = repository.findById(dados.id());

        Tag tag = tagOptional.get();
        tag.atualizarTag(dados);

        return ResponseEntity.ok(tag);
    }

    @GetMapping
    public ResponseEntity<List<Tag>> listarTags() {
        List<Tag> tags = repository.findAll();
        return ResponseEntity.ok(tags);
    }
    
}
