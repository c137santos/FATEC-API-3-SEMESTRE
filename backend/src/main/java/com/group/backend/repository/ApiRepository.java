package com.group.backend.repository;

import com.group.backend.entity.ApiPublica;
import com.group.backend.repository.ApiRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiRepository extends JpaRepository<ApiPublica, Long> {


}