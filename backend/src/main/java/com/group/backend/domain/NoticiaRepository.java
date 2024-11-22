package com.group.backend.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.group.backend.entity.Noticia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface NoticiaRepository extends JpaRepository<Noticia, Long> {

    @Query("SELECT n FROM Noticia n")
    Page<Noticia> findAllNoticias(Pageable pageable);

    @Query("SELECT n FROM Noticia n " +
           "LEFT JOIN n.tagNoticia tn " +  // Utilizando LEFT JOIN para garantir que as tags sejam buscadas
           "LEFT JOIN tn.tagId t " +
           "WHERE (:tags IS NULL OR t.tagNome IN :tags) " +
           "AND (:portals IS NULL OR n.portal.nome IN :portals) " +
           "AND (:reporters IS NULL OR n.reporte.nome IN :reporters) " +
           "AND (:startDate IS NULL OR n.notiData >= :startDate) " +
           "AND (:endDate IS NULL OR n.notiData <= :endDate) " +
           "AND (:keyword IS NULL OR LOWER(n.notiText) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Noticia> findByFilters(
        @Param("tags") List<String> tags,
        @Param("portals") List<String> portals,
        @Param("reporters") List<String> reporters,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate,
        @Param("keyword") String keyword,
        Pageable pageable
    );

    boolean existsByUrl(String url);

    Noticia findByUrl(String url);

    @Query("SELECT MAX(n.notiId) FROM Noticia n")
    Long findMaxNotiId();
}
