package com.group.backend.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group.backend.entity.Noticia;

@Repository
public interface NoticiaRepository extends JpaRepository<Noticia, Long> {
    
}

