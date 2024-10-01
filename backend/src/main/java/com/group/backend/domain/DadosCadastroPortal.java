package com.group.backend.domain;

import java.time.LocalDate;

public record DadosCadastroPortal(
    String portalNome,
    String portalUrl,
    boolean portalAtivo,
    String portalFrequencia,
    LocalDate portalData
) {}
