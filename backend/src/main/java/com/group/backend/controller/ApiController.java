package com.group.backend.controller;

import com.group.backend.entity.Api;
import com.group.backend.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ListarApis")
public class ApiController {
    private final ApiService apiService;

    @Autowired
    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping
    public ResponseEntity<List<Api>> getAllApis() {
        List<Api> apis = apiService.getAllApis();
        return ResponseEntity.ok(apis);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Api> getApiById(@PathVariable Long id) {
        return apiService.getApiById(id)
                .map(api -> ResponseEntity.ok(api))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}

