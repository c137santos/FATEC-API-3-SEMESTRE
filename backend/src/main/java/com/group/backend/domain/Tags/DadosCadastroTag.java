package com.group.backend.domain.Tags;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroTag(
        @NotBlank
        String nome,
        String descricao) {

}


