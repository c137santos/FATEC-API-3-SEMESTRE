package com.group.backend.service;

import com.group.backend.entity.ApiPublica;
import com.group.backend.repository.ApiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ApiService {

    private final ApiRepository apiRepository;


    @Autowired
    public ApiService(ApiRepository apiRepository) {
        this.apiRepository = apiRepository;
    }

    public ApiPublica cadastrarApiPublica(ApiPublica apiPublica) {
        return apiRepository.save(apiPublica);
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

}