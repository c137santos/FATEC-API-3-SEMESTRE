package com.group.backend.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group.backend.domain.ApiRepository;
import com.group.backend.entity.ApiPublica;

import jakarta.transaction.Transactional;

@RequestMapping("/apis")
@RestController
@CrossOrigin(origins = "*")
public class ApiController {

    private final ApiRepository apiRepository;

    public ApiController(ApiRepository apiRepository) {
        this.apiRepository = apiRepository;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ApiPublica>> listarApis() {
    List<ApiPublica> apis = apiRepository.findAll();
    for (ApiPublica apiP : apis) {
        System.out.println(apiP);
    }
    
    return ResponseEntity.ok(apis);
    }
}