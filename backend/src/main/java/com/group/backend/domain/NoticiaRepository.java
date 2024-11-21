package com.group.backend.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.group.backend.entity.Noticia;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface NoticiaRepository extends JpaRepository<Noticia, Long> {

    @Query("SELECT n FROM Noticia n " +
           "WHERE (:tag IS NULL OR EXISTS (SELECT t FROM n.tagNoticia t WHERE t.tagId.tagNome = :tag))")
    Page<Noticia> findByTag(@Param("tag") String tag, Pageable pageable);

    boolean existsByUrl(String url);
    Noticia findByUrl(String url);

    @Query("SELECT MAX(n.notiId) FROM Noticia n")
    Long findMaxNotiId();
}
