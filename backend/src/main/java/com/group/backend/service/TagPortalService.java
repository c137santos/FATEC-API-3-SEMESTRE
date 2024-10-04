package com.group.backend.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group.backend.domain.TagPortalRepository;
import com.group.backend.domain.TagRepository;
import com.group.backend.entity.Portal;
import com.group.backend.entity.TagPortal;
import com.group.backend.entity.Tag;


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
        Tag tag = tagRepository.findById(tagId).orElseThrow(() -> new IllegalArgumentException("Tag not found with id: " + tagId));
        TagPortal tagPortal = new TagPortal(tag, portal);
        return tagPortalRepository.save(tagPortal);
    }
}
