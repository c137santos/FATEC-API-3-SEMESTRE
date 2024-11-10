package com.group.backend.domain;

public record DadosEdicaoApiPublica(
    String nome,
    String frequencia,
	String descricao,
	Boolean active,
	String url
) {}
