package com.group.backend.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group.backend.entity.Regionalismo;

@Repository
public interface RegionalismoRepository extends JpaRepository<Regionalismo, Long> {
    
}
