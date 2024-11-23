package com.group.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.group.backend.domain.ApiPublicaRepository;
import com.group.backend.domain.DadosEdicaoApiPublica;
import com.group.backend.domain.ResultApiRepository;
import com.group.backend.entity.ApiPublica;
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

    @PutMapping("/editar/{id}")
    public ResponseEntity<ApiPublica> editarApi(@PathVariable Long id, @RequestBody DadosEdicaoApiPublica apiAtualizada) {
        Optional<ApiPublica> optionalApi = apiRepository.findById(id);
        if (optionalApi.isPresent()) {
            ApiPublica api = optionalApi.get();
            api.setNome(apiAtualizada.nome());
            api.setUrl(apiAtualizada.url());
            api.setFrequencia(apiAtualizada.frequencia());
            api.setActive(apiAtualizada.active());
            api.setDescricao(apiAtualizada.descricao());
            apiRepository.save(api);
            ApiPublica novaApi = apiRepository.findById(id).orElse(null);
            return ResponseEntity.ok(novaApi);
        } else {
            return ResponseEntity.notFound().build();
        }
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
