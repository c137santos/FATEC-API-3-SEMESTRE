package com.group.backend.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group.backend.entity.Reporter;
import com.group.backend.entity.Portal;

@Repository
public interface ReporterRepository extends JpaRepository<Reporter, Long> {
    // Busca um repórter pelo nome
    Reporter findByNome(String nome);

    // Busca um repórter pelo nome e pelo portal
    Reporter findByNomeAndPortal(String nome, Portal portal);

    // Busca repórteres por ID do portal
    List<Reporter> findByPortalId(Long portalId);
}
