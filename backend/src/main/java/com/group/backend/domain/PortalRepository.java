package com.group.backend.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group.backend.entity.Portal;

@Repository
public interface PortalRepository extends JpaRepository<Portal, Long> {
    
}
