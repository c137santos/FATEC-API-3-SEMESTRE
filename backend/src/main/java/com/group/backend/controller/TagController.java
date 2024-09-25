package com.group.backend.controller;

import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group.backend.domain.DadosCadastroTag;
import com.group.backend.domain.TagRepository;
import com.group.backend.entity.Tag;

@RequestMapping("/tags")
@RestController
@CrossOrigin(origins = "*")
public class TagController {

    private final TagRepository tagRepository;

    public TagController(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity<Tag> cadastrarTag(@RequestBody DadosCadastroTag dados) {
        Tag novaTag = new Tag();
        novaTag.setTagNome(dados.tagNome());
        novaTag.setTagDescricao(dados.tagDescricao());
        novaTag.setTagActive(dados.tagActive());
        novaTag.setTagData(new Date());

        Tag tagSalva = tagRepository.save(novaTag);
        return ResponseEntity.ok(tagSalva);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Tag>> listarTags() {
        List<Tag> tags = tagRepository.findAll();
        return ResponseEntity.ok(tags);
    }
}
