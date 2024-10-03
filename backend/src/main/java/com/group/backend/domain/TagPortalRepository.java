package com.group.backend.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group.backend.entity.TagPortal;
import com.group.backend.entity.TagPortalId;

@Repository
public interface TagPortalRepository extends JpaRepository<TagPortal, TagPortalId> {

    TagPortalId save(TagPortalId novoTagPortal);
    
}
