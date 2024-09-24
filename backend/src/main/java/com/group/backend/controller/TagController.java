package com.group.backend.controller;

import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group.backend.domain.DadosCadastroTag;
import com.group.backend.domain.TagRepository;
import com.group.backend.entity.Tag;

@RequestMapping("/tags/cadastrar")
@RestController
@CrossOrigin(origins = "*")
public class TagController {
    private final TagRepository tagRepository;

    public TagController(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @PostMapping
    public ResponseEntity<Tag> cadastrarTag(@RequestBody DadosCadastroTag dados) {
        Tag novaTag = new Tag();
        novaTag.setTagNome(dados.tagNome());
        novaTag.setTagDescricao(dados.tagDescricao());
        novaTag.setTagActive(dados.tagActive());
        Date today = new Date();
        novaTag.setTagData(today);

        Tag tagSalva = tagRepository.save(novaTag);
        return ResponseEntity.ok(tagSalva);
    }
}
