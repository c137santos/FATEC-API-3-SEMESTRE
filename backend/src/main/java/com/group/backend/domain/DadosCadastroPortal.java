package com.group.backend.domain;

import java.time.LocalDate;

public record DadosCadastroPortal(
    Long portalId,
    Long tagId,
    String portalNome,
    String portalUrl,
    String portalFrequencia,
    LocalDate portalData
) {}
