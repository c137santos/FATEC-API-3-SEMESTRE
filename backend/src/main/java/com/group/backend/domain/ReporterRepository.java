package com.group.backend.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group.backend.entity.Reporter;

@Repository
public interface ReporterRepository extends JpaRepository<Reporter, Long> {
    Reporter findByNome(String nome);
    List<Reporter> findByPortalId(Long portal);

}