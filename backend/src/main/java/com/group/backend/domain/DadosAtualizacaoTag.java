package com.group.backend.domain;


import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoTag(
        @NotNull
        Long id,
        String nome,
        String descricao) {
}
