package com.group.backend.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.group.backend.entity.Portal;

@Repository
public interface PortalRepository extends JpaRepository<Portal, Long> {
    List<Portal> findByAtivoTrue();

    @Query("SELECT DISTINCT p FROM Portal p " +
           "JOIN p.tagPortals tp " +
           "JOIN tp.tag t " +
           "WHERE p.ativo = true " +
           "AND t.tagActive = true " +
           "AND p.frequencia = :frequencia")
    List<Portal> findByFrequenciaAndActiveTags(@Param("frequencia") String frequencia);

    Portal findByUrl(String url);

    @Query("SELECT p FROM Portal p " +
           "WHERE LOWER(FUNCTION('REPLACE', FUNCTION('REPLACE', p.url, 'http://', ''), 'https://', '')) = " +
           "LOWER(FUNCTION('REPLACE', FUNCTION('REPLACE', :url, 'http://', ''), 'https://', ''))")
    Portal findByNormalizedUrl(@Param("url") String url);
}
