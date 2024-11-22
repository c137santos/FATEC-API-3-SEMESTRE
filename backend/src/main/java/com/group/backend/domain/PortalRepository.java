package com.group.backend.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
}
