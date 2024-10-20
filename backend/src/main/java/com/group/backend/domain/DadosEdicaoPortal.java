package com.group.backend.domain;

public record DadosEdicaoPortal(
    String nome,
    String url,
    boolean ativo,
    String frequencia
) {}