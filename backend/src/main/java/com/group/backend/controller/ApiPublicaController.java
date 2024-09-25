package com.group.backend.controller;

import com.group.backend.entity.ApiPublica;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.group.backend.service.ApiService;


@RestController
@RequestMapping("/apis/cadastrar")
@CrossOrigin(origins = "*")
public class ApiPublicaController {
    private final ApiService apiService;

    public ApiPublicaController(ApiService apiService) {
        this.apiService = apiService;
    }

    @PostMapping
    public ResponseEntity<ApiPublica> cadastrarApi(@RequestBody ApiPublica apiPublica) {
        ApiPublica novaApi = apiService.cadastrarApiPublica(apiPublica);
        return ResponseEntity.ok(novaApi);
    }
}