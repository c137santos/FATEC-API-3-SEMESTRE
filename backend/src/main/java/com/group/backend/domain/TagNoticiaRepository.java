package com.group.backend.domain;

import com.group.backend.entity.TagNoticia;
import com.group.backend.entity.TagNoticiaId;
import com.group.backend.entity.Noticia;
import com.group.backend.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagNoticiaRepository extends JpaRepository<TagNoticia, TagNoticiaId> {
    
}
