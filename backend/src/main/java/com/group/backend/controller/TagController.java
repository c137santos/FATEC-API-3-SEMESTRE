package com.group.backend.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group.backend.domain.DadosAtualizarTag;
import com.group.backend.domain.DadosCadastroTag;
import com.group.backend.domain.TagRepository;
import com.group.backend.entity.Tag;

import jakarta.transaction.Transactional;

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
        novaTag.setTagData(LocalDate.now());

        Tag tagSalva = tagRepository.save(novaTag);
        return ResponseEntity.ok(tagSalva);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Tag>> listarTags() {
    List<Tag> tags = tagRepository.findAll();
    
    for (Tag tag : tags) {
        System.out.println("Tag: " + tag.getTagNome() + ", ID: " + tag.getTagId());
    }
    
    return ResponseEntity.ok(tags);
}

    @PutMapping("/editar/{id}")
    @Transactional
    public ResponseEntity<Tag> editarTag(@PathVariable Long id, @RequestBody DadosAtualizarTag dados) {
        Optional<Tag> tagExistente = tagRepository.findById(id);

        if (tagExistente.isPresent()) {
            Tag tagParaEditar = tagExistente.get();
            tagParaEditar.setTagNome(dados.tagNome());
            tagParaEditar.setTagDescricao(dados.tagDescricao());
            tagParaEditar.setTagActive(dados.tagActive());
            tagParaEditar.setTagData(LocalDate.now());

            Tag tagAtualizada = tagRepository.save(tagParaEditar);
            return ResponseEntity.ok(tagAtualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}