package com.group.backend.controller;

import com.group.backend.entity.ApiPublica;
import com.group.backend.service.ApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apis/cadastrar")
@CrossOrigin(origins = "*")
public class ClientController {

    private final ApiService apiService;

    public ClientController(ApiService apiService) {
        this.apiService = apiService;
    }

    @PostMapping
    public ResponseEntity<ApiPublica> cadastrarApi(@RequestBody ApiPublica apiPublica) {
        ApiPublica novaApi = apiService.cadastrarApiPublica(apiPublica);
        return ResponseEntity.ok(novaApi);
    }

    

}
