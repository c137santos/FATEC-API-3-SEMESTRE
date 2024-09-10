package com.group.backend.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

    void save(Tag tag);
}

