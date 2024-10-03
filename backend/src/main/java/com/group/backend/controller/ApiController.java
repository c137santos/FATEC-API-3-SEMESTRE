package com.group.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group.backend.entity.ApiPublica;
import com.group.backend.domain.ApiPublicaRepository;
import com.group.backend.domain.ResultApiRepository;
import com.group.backend.entity.ResultApi;

@RequestMapping("/apis")
@RestController
@CrossOrigin(origins = "*")
public class ApiController {

    private final ApiPublicaRepository apiRepository;
    private final ResultApiRepository resultApiRepository;

    public ApiController(ApiPublicaRepository apiRepository, ResultApiRepository resultApiRepository) {
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

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ApiPublica> buscarApiPorId(@PathVariable Long id) {
        Optional<ApiPublica> apiOptional = apiRepository.findById(id);
        if (apiOptional.isPresent()) {
            return ResponseEntity.ok(apiOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<ApiPublica> editarApi(@PathVariable Long id, @RequestBody ApiPublica apiAtualizada) {
        Optional<ApiPublica> apiOptional = apiRepository.findById(id);
        if (apiOptional.isPresent()) {
            ApiPublica apiExistente = apiOptional.get();
            apiExistente.setApiNome(apiAtualizada.getApiNome());
            apiExistente.setApiFrequencia(apiAtualizada.getApiFrequencia());
            apiExistente.setApiDescricao(apiAtualizada.getApiDescricao());
            apiExistente.setApiUrl(apiAtualizada.getApiUrl());
            apiExistente.setApiActive(apiAtualizada.isApiActive());
            apiExistente.setApiData(new java.util.Date());
            ApiPublica apiSalva = apiRepository.save(apiExistente);
            return ResponseEntity.ok(apiSalva);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
