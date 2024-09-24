package com.group.backend.domain;

import java.util.Date;

public record DadosCadastroApiPublica(
    String nome,
    String frequencia,
    Date proximoCrawlData,
	String descricao,
	Boolean active,
	String url
) {}
