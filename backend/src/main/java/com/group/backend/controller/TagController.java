package com.group.backend.controller;


import Domain.DadosAtualizacaoTags;
import Domain.DadosCadastroTag;
import Domain.Tag;
import Domain.TagRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RequestMapping("tags")
@RestController
public class TagController {

    @Autowired
    private TagRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrarTag(@RequestBody @Valid DadosCadastroTag dados, UriComponentsBuilder uriBuilder) {
        var tag = new Tag(dados);
        repository.save(tag);

        var uri = uriBuilder.path("/tags/{id}").buildAndExpand(tag.getId()).toUri();

        return ResponseEntity.created(uri).body(tag);
    }
    @PutMapping
    @Transactional
    public ResponseEntity atualizarTag(@RequestBody @Valid DadosAtualizacaoTags dados) {
        var tag = repository.getReferenceById(dados.id());
        tag.atualizarTag(dados);

        return ResponseEntity.ok(tag);
    }

    @GetMapping
    public Page<Tag> listarTags(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
        return repository.findAll(pageable);
    }
}

