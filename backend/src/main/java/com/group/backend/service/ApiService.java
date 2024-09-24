package com.group.backend.service;

import com.group.backend.entity.ApiPublica;
import com.group.backend.repository.ApiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ApiService {

    private final ApiRepository apiRepository;

    // O período de captura é fixo, "diariamente" neste exemplo
    private static final String PERIODO_CAPTURA_FIXO = "diariamente";

    @Autowired
    public ApiService(ApiRepository apiRepository) {
        this.apiRepository = apiRepository;
    }

    public ApiPublica cadastrarApiPublica(ApiPublica apiPublica) {
        return apiRepository.save(apiPublica);
    }

    public ApiPublica salvarApi(String api_nome, String api_frequencia, String api_descricao, boolean api_active, String api_url) {
        ApiPublica api = new ApiPublica();
        api.setNome(api_nome);
        api.setFrequencia(api_frequencia);
        api.setDescricao(api_descricao);
        api.setActive(api_active);
        api.setUrl(api_url);

        // Definindo a data futura de captura com base no período
        LocalDate dataFuturaCaptura = calcularDataFuturaCaptura(api_frequencia);
        api.setDataFuturaCaptura(dataFuturaCaptura);

        return apiRepository.save(api);
    }

    // Calcula a data futura de captura com base na frequência
    public LocalDate calcularDataFuturaCaptura(String frequencia) {
        switch (frequencia.toLowerCase()) {
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
