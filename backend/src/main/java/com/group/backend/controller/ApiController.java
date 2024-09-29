package com.group.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group.backend.domain.ApiRepository;
import com.group.backend.entity.ApiPublica;


@RequestMapping("/apis")
@RestController
@CrossOrigin(origins = "*")
public class ApiController {

    private final ApiRepository apiRepository;

    public ApiController(ApiRepository apiRepository) {
        this.apiRepository = apiRepository;
    }

    @GetMapping("listar")
    public ResponseEntity<List<ApiPublica>> listarApis() {
    List<ApiPublica> apis = apiRepository.findAll();
    for (ApiPublica apiP : apis) {
        System.out.println(apiP);
    }
    
    return ResponseEntity.ok(apis);
    }

    @PostMapping("cadastrar")
    public ResponseEntity<ApiPublica> cadastrarApi(@RequestBody ApiPublica apiPublica) {
        ApiPublica novaApi = apiRepository.save(apiPublica);
        return ResponseEntity.ok(novaApi);
    }

}