package com.group.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

import com.group.backend.domain.TagRepository;
import com.group.backend.entity.Tag;

@Service
public class TagService {

    private final TagRepository tagRepository;
    
    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }
    
    public Tag cadastrarTag(String tagNome, String tagDescricao, boolean tagActive) {
        Tag novaTag = new Tag();
        novaTag.setTagNome(tagNome);
        novaTag.setTagDescricao(tagDescricao);
        novaTag.setTagActive(tagActive);
        novaTag.setTagData(LocalDate.now());
        Tag tagSalva = tagRepository.save(novaTag);
        return tagSalva;
    }

    public List<Tag> listarTag() {
        List<Tag> tags = tagRepository.findAll();
        return tags;
    }


}
