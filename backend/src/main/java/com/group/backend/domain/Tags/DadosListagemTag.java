package com.group.backend.domain.Tags;

public record DadosListagemTag(Long id, String nome, String descricao, Boolean ativo) {

    public DadosListagemTag(Tag tag) {
        this(tag.getId(), tag.getNome(), tag.getDescricao(), tag.isAtivo());
    }
}
