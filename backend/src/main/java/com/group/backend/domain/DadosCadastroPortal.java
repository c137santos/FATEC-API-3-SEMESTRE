package com.group.backend.domain;

public record DadosCadastroPortal(
    String portalNome,
    String portalUrl,
    String portalFrequencia,
    Long tagId
) {}
