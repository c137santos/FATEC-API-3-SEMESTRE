package com.group.backend.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group.backend.domain.TagPortalRepository;
import com.group.backend.domain.TagRepository;
import com.group.backend.entity.Portal;
import com.group.backend.entity.Tag;
import com.group.backend.entity.TagPortal;

@Service
public class TagPortalService {
    
    private final TagPortalRepository tagPortalRepository;
    private final TagRepository tagRepository;

    @Autowired
    public TagPortalService(TagPortalRepository tagPortalRepository, TagRepository tagRepository){
        this.tagPortalRepository = tagPortalRepository;
        this.tagRepository = tagRepository;
    }

    public TagPortal cadastrarTagPortal(Long tagId, Portal portal) {

        Tag tag = tagRepository.findById(tagId).orElseThrow(() -> new IllegalArgumentException("Tag not found"));
        
        TagPortal tagPortal = new TagPortal(tag, portal);
        TagPortal novoTagPortal = tagPortalRepository.save(tagPortal);
        return novoTagPortal;
    }
}
