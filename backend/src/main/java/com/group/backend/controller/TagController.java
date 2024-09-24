package com.group.backend.controller;

import java.util.Date;
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

    // Endpoint para cadastrar tags
    @PostMapping("/cadastrar")
    public ResponseEntity<Tag> cadastrarTag(@RequestBody DadosCadastroTag dados) {
        Tag novaTag = new Tag();
        novaTag.setTagNome(dados.tagNome());
        novaTag.setTagDescricao(dados.tagDescricao());
        novaTag.setTagActive(dados.tagActive());
        novaTag.setTagData(new Date()); // Atualiza automaticamente a data ao salvar

        Tag tagSalva = tagRepository.save(novaTag);
        return ResponseEntity.ok(tagSalva);
    }

    // Endpoint para listar todas as tags
    @GetMapping("/listar")
    public ResponseEntity<List<Tag>> listarTags() {
        List<Tag> tags = tagRepository.findAll();
        return ResponseEntity.ok(tags);
    }

    // Endpoint para editar uma tag existente
    @PutMapping("/editar/{id}")
    public ResponseEntity<Tag> editarTag(@PathVariable Long id, @RequestBody DadosCadastroTag dados) {
        Optional<Tag> tagExistente = tagRepository.findById(id);

        if (tagExistente.isPresent()) {
            Tag tagParaEditar = tagExistente.get();
            tagParaEditar.setTagNome(dados.tagNome());
            tagParaEditar.setTagDescricao(dados.tagDescricao());
            tagParaEditar.setTagActive(dados.tagActive());
            tagParaEditar.setTagData(new Date()); // Atualiza a data de modificação automaticamente

            Tag tagAtualizada = tagRepository.save(tagParaEditar);
            return ResponseEntity.ok(tagAtualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
