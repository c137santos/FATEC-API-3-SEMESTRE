package com.group.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group.backend.domain.ApiRepository;
import com.group.backend.entity.ApiPublica;

import com.group.backend.domain.ResultApiRepository;
import com.group.backend.entity.ResultApi;



@RequestMapping("/apis")
@RestController
@CrossOrigin(origins = "*")
public class ApiController {

    private final ApiRepository apiRepository;
    private final ResultApiRepository resultApiRepository;

    public ApiController(ApiRepository apiRepository, ResultApiRepository resultApiRepository) {
        this.apiRepository = apiRepository;
        this.resultApiRepository = resultApiRepository;

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

    @GetMapping("resultados")
    public ResponseEntity<List<ResultApi>> getAllResultsByApiId(@RequestParam Long apiId) {
        List<ResultApi> results = resultApiRepository.findAllByApiId(apiId);
        
        if (!results.isEmpty()) {
            return ResponseEntity.ok(results);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
