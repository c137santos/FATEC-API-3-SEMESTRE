package com.group.backend.domain;

public record DadosCadastroPortal(
    String portalNome,
    String portalUrl,
    boolean portalAtivo,
    String portalFrequencia,
    Long tagId
) {}
