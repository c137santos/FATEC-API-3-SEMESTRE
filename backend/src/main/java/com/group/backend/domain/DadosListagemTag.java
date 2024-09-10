package com.group.backend.domain;

public record DadosListagemTag(Long id, String nome, String descricao) {

    public DadosListagemTag(Tag tag) {
        this(tag.getId(), tag.getNome(), tag.getDescricao());
    }
}
