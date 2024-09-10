package com.group.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.group.backend.domain.Tags.DadosAtualizacaoTag;
import com.group.backend.domain.Tags.DadosCadastroTag;
import com.group.backend.domain.Tags.Tag;
import com.group.backend.domain.Tags.TagRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RequestMapping("tags")
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
        var tag = repository.getReferenceById(dados.id());
        tag.atualizarTag(dados);

        return ResponseEntity.ok(tag);
    }

    @GetMapping
    public ResponseEntity<Page<Tag>> listarTags(@PageableDefault(size = 10) Pageable pageable) {
        Page<Tag> tags = repository.findAll(pageable); 
        return ResponseEntity.ok(tags);
    }
    
}
