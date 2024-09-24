package com.group.backend.controller;

import com.group.backend.entity.ApiPublica;
import com.group.backend.service.ApiPublicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/ListarApis")
public class ApiPublicaController {
    private final ApiPublicaService apiPublicaService;

    @Autowired
    public ApiPublicaController(ApiPublicaService apiPublicaService) {
        this.apiPublicaService = apiPublicaService;
    }

    @GetMapping
    public ResponseEntity<List<ApiPublica>> getAllApis() {
        List<ApiPublica> apis = apiPublicaService.getAllApis();
        return ResponseEntity.ok(apis);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiPublica> getApiById(@PathVariable Long id) {
        return apiPublicaService.getApiById(id)
                .map(api -> ResponseEntity.ok(api))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}

