package com.group.backend.service;

import com.group.backend.model.Api;
import com.group.backend.repository.ApiRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ApiService {

    private final ApiRepository apiRepository;

    // O período de captura é fixo, "diariamente" neste exemplo
    private static final String PERIODO_CAPTURA_FIXO = "diariamente";

    public ApiService(ApiRepository apiRepository) {
        this.apiRepository = apiRepository;
    }

    public Api salvarApi(String nome, String url) {
        Api api = new Api();
        api.setNome(nome);
        api.setUrl(url);
        api.setPeriodoCaptura(PERIODO_CAPTURA_FIXO);

        // Definindo a data futura de captura com base no período
        LocalDate dataFuturaCaptura = calcularDataFuturaCaptura();
        api.setDataFuturaCaptura(dataFuturaCaptura);

        return apiRepository.save(api);
    }

    // Calcula a data futura de captura com base no período
    private LocalDate calcularDataFuturaCaptura() {
        return LocalDate.now().plusDays(1); // Se for diariamente
    }

    public List<Api> listarApis() {
        return apiRepository.findAll();
    }
}