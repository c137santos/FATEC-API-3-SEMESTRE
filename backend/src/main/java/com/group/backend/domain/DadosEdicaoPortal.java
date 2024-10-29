package com.group.backend.domain;

import java.util.List;

public record DadosEdicaoPortal(
    String nome,
    String url,
    boolean ativo,
    String frequencia,
    List<Long> tagsSelecionadas

) {}