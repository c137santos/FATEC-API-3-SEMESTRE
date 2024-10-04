package com.group.backend.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group.backend.domain.TagPortalRepository;
import com.group.backend.domain.TagRepository;
import com.group.backend.entity.TagPortalId;


@Service
public class TagPortalService {
    
    private final TagPortalRepository tagPortalRepository;

    @Autowired
    public TagPortalService(TagPortalRepository tagPortalRepository, TagRepository tagRepository){
        this.tagPortalRepository = tagPortalRepository;
    }

    public TagPortalId cadastrarTagPortal(Long tagId, Long portalId) {
        
        TagPortalId tgi = new TagPortalId(tagId, portalId);
        TagPortalId novoTagPortal = tagPortalRepository.save(tgi);
        return novoTagPortal;
    }
}
