package com.group.backend.service;

import com.group.backend.repository.ApiRepository;
import org.springframework.stereotype.Service;
import com.group.backend.entity.ApiPublica;
import com.group.backend.dto.ApiDto;
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

    public ApiPublica salvarApi(String nome, String url) {
        ApiPublica api = new ApiPublica();
        api.setApiNome(nome);
        api.setApiFrequencia(api_frequencia);
        api.setApiDescricao(api_descricao);
        api.setApiActive(api_active);
        api.setApiUrl(url);
        api.setApiPeriodoCaptura(PERIODO_CAPTURA_FIXO);

        // Definindo a data futura de captura com base no período
        LocalDate dataFuturaCaptura = calcularDataFuturaCaptura();
        api.setDataFuturaCaptura(dataFuturaCaptura);

        return apiRepository.save(api);
    }

    // Calcula a data futura de captura com base no período
    
        private LocalDate calcularDataFuturaCaptura() {
        return LocalDate.now().plusDays(1); // Se for diariamente
    }

    public List<ApiPublica> listarApis() {
        return apiRepository.findAll();
    }
}