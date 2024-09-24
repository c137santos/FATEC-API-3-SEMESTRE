package com.group.backend.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group.backend.entity.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    
}
