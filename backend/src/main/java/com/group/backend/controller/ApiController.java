package com.group.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.group.backend.model.Api;
import com.group.backend.service.ApiService;
import com.group.backend.dto.ApiDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final ApiService apiService;

    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    // Endpoint para salvar a API
    @PostMapping("/salvar")
    public ResponseEntity<Api> salvarApi(@RequestBody ApiDto apiDto) {
        Api novaApi = apiService.salvarApi(apiDto.getNome(), apiDto.getUrl());
        return ResponseEntity.ok(novaApi);
    }

    // Endpoint para listar todas as APIs
    @GetMapping("/listar")
    public ResponseEntity<List<Api>> listarApis() {
        List<Api> apis = apiService.listarApis();
        return ResponseEntity.ok(apis);
    }
}


