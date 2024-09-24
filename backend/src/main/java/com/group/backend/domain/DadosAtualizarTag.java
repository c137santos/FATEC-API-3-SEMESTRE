package com.group.backend.domain;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizarTag(
    String tagNome,    // Nome opcional para ser atualizado
    String tagDescricao,  // Descrição opcional para ser atualizada
    Boolean tagActive   // Status ativo/inativo opcional para ser atualizado
) {}
