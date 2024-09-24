package com.group.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group.backend.domain.DadosCadastroApiPublica;
import com.group.backend.domain.ApiPublicaRepository;
import com.group.backend.entity.ApiPublica;


@RequestMapping("/apiPublicas")
@RestController
public class ApiPublicaController {
    private final ApiPublicaRepository apiPublicaRepository;

    public ApiPublicaController(ApiPublicaRepository apiPublicaRepository) {
        this.apiPublicaRepository = apiPublicaRepository;
    }

    @PostMapping
    public ResponseEntity<ApiPublica> cadastrarApiPublica(@RequestBody DadosCadastroApiPublica dados) {
        ApiPublica novaApiPublica = new ApiPublica();
        
		novaApiPublica.setNome(dados.nome());
        novaApiPublica.setFrequencia(dados.frequencia());
        novaApiPublica.setDescricao(dados.descricao());
        novaApiPublica.setActive(true);
        novaApiPublica.setUrl(dados.url());

        ApiPublica apiPublicaSalva = apiPublicaRepository.save(novaApiPublica);

        return ResponseEntity.ok(apiPublicaSalva);
	}
}
