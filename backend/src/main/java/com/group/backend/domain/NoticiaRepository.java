package com.group.backend.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.group.backend.entity.Noticia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface NoticiaRepository extends JpaRepository<Noticia, Long> {

    @Query("SELECT n FROM Noticia n")
    Page<Noticia> findAllNoticias(Pageable pageable);

    @Query("SELECT n FROM Noticia n " +
           "WHERE (:tags IS NULL OR EXISTS (SELECT t FROM n.tagNoticia t WHERE t.tagId.tagNome IN :tags)) " +
           "AND (:portals IS NULL OR n.portal.nome IN :portals) " +
           "AND (:reporters IS NULL OR n.reporte.nome IN :reporters)")
    Page<Noticia> findByTagsPortalsAndReporters(
        @Param("tags") List<String> tags,
        @Param("portals") List<String> portals,
        @Param("reporters") List<String> reporters,
        Pageable pageable
    );

    boolean existsByUrl(String url);

    Noticia findByUrl(String url);

    @Query("SELECT MAX(n.notiId) FROM Noticia n")
    Long findMaxNotiId();
}
