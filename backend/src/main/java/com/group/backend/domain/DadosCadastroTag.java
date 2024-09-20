package com.group.backend.domain;

import java.util.Date;

public record DadosCadastroTag(
    String tagNome,
    String tagDescricao,
    boolean tagActive,
    Date tagData
) {}
