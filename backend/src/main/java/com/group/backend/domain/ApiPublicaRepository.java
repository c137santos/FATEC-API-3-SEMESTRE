package com.group.backend.domain;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.group.backend.entity.ApiPublica;

@Repository
public interface ApiPublicaRepository extends JpaRepository<ApiPublica, Long> {
	@Query(value = "SELECT * FROM api_publica WHERE api_frequencia='diariamente' AND api_active=true", nativeQuery = true)
    ArrayList<ApiPublica> getDailyList();
	@Query(value = "SELECT * FROM api_publica WHERE api_frequencia='semanalmente' AND api_active=true", nativeQuery = true)
    ArrayList<ApiPublica> getWeeklyList();
	@Query(value = "SELECT * FROM api_publica WHERE api_frequencia='mensalmente' AND api_active=true", nativeQuery = true)
    ArrayList<ApiPublica> getMonthlyList();
}
