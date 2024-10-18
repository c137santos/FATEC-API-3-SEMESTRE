package com.group.backend.domain;

import com.group.backend.entity.Regionalismo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionalismoRepository extends JpaRepository<Regionalismo, Long> {
    
}
