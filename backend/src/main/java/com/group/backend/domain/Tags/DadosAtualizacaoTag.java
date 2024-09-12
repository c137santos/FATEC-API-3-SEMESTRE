package com.group.backend.domain.Tags;


import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoTag(
        @NotNull
        Long id,
        String nome,
        String descricao,
        Boolean ativo) {
}
