package com.group.backend.service;

import com.group.backend.domain.TagPortalRepository;
import com.group.backend.entity.TagPortalId;

public class TagService {
    private static TagPortalRepository tagPortalRepository;

    public TagService() {}

    public TagPortalId cadastrarTagPortal(Long tagId, Long portalId) {

        TagPortalId novoTagPortal = new TagPortalId();
        novoTagPortal.setTag(tagId);
        novoTagPortal.setPortal(portalId);

        TagPortalId tagPortalSalvo = tagPortalRepository.save(novoTagPortal);
        return tagPortalSalvo;
    }
}
