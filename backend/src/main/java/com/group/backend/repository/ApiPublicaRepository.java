package com.group.backend.repository;
import com.group.backend.entity.ApiPublica;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface ApiPublicaRepository extends JpaRepository<ApiPublica, Long> {
    
}

