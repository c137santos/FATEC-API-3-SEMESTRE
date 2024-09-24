package com.group.backend.service;

import com.group.backend.repository.ApiRepository;
import org.springframework.stereotype.Service;
import com.group.backend.entity.ApiPublica;
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

    public ApiPublica salvarApi(String api_nome, String api_frequencia, String api_descricao, boolean api_active, String api_url) {
        ApiPublica api = new ApiPublica();
        api.setApiNome(api_nome);
        api.setApiFrequencia(api_frequencia);
        api.setApiDescricao(api_descricao);
        api.setApiActive(api_active);
        api.setApiUrl(api_url);
        api.setApiPeriodoCaptura(PERIODO_CAPTURA_FIXO);

        // Definindo a data futura de captura com base no período
        LocalDate dataFuturaCaptura = calcularDataFuturaCaptura();
        api.setDataFuturaCaptura(dataFuturaCaptura);

        return apiRepository.save(api);
    }

    // Calcula a data futura de captura com base no período
    
    public LocalDate calcularDataFuturaCaptura() {
        switch (PERIODO_CAPTURA_FIXO.toLowerCase()) {
            case "semanalmente":
                return LocalDate.now().plusWeeks(1);
            case "mensalmente":
                return LocalDate.now().plusMonths(1);
            default: // Considera o padrão como "diariamente"
                return LocalDate.now().plusDays(1);
        }
    }

    public List<ApiPublica> listarApis() {
        return apiRepository.findAll();
    }
}