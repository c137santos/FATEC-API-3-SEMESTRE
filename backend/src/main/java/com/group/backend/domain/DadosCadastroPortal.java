package com.group.backend.domain;

import java.util.List;

public record DadosCadastroPortal(
    String portalNome,
    String portalUrl,
    boolean portalAtivo,
    String portalFrequencia,
    List<Long> tagsSelecionadas
) {}
