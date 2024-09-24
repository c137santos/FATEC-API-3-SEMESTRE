package com.group.backend.service;

import com.group.backend.entity.ApiPublica;
import com.group.backend.repository.ApiPublicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApiPublicaService {
    private final ApiPublicaRepository apiPublicaRepository;

    @Autowired
    public ApiPublicaService(ApiPublicaRepository apiPublicaRepository) {
        this.apiPublicaRepository = apiPublicaRepository;
    }

    public List<ApiPublica> getAllApis() {
        return apiPublicaRepository.findAll();
    }

    public Optional<ApiPublica> getApiById(Long id) {
        return apiPublicaRepository.findById(id);
    }
}
