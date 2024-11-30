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

    @Query("SELECT DISTINCT n FROM Noticia n " +
           "LEFT JOIN FETCH n.portal p " +
           "LEFT JOIN FETCH n.reporte r " +
           "LEFT JOIN n.tagNoticia tn " +
           "LEFT JOIN tn.tagId t " +
           "WHERE (:tagsIsNull = TRUE OR t.tagNome IN :tags) " +
           "AND (:portalsIsNull = TRUE OR p.nome IN :portals) " +
           "AND (:reportersIsNull = TRUE OR r.nome IN :reporters) " +
           "AND (:startDateIsNull = TRUE OR n.notiData >= :startDate) " +
           "AND (:endDateIsNull = TRUE OR n.notiData <= :endDate) " +
           "AND (:keywordIsNull = TRUE OR LOWER(n.notiText) LIKE LOWER(CONCAT('%', :keyword, '%'))) ")
    Page<Noticia> findByFilters(
        @Param("tagsIsNull") boolean tagsIsNull,
        @Param("tags") List<String> tags,
        @Param("portalsIsNull") boolean portalsIsNull,
        @Param("portals") List<String> portals,
        @Param("reportersIsNull") boolean reportersIsNull,
        @Param("reporters") List<String> reporters,
        @Param("startDateIsNull") boolean startDateIsNull,
        @Param("startDate") LocalDate startDate,
        @Param("endDateIsNull") boolean endDateIsNull,
        @Param("endDate") LocalDate endDate,
        @Param("keywordIsNull") boolean keywordIsNull,
        @Param("keyword") String keyword,
        Pageable pageable
    );

    boolean existsByUrl(String url);

    Noticia findByUrl(String url);

    @Query("SELECT MAX(n.notiId) FROM Noticia n")
    Long findMaxNotiId();
}
