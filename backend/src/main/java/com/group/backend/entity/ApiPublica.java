package com.group.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "api_publica")
@NoArgsConstructor
public class ApiPublica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "api_id")
    @Getter
    private Long id;

    @Column(name = "api_nome", nullable = false)
    @Getter
    private String nome;

    @Column(name = "api_frequencia", nullable = false)
    @Getter
    private String frequencia;

    @Column(name = "api_descricao")
    @Getter
    private String descricao;

    @Column(name = "api_active")
    @Getter
    private Boolean active;

    @Column(name = "api_url", length = 2048)
    @Getter
    private String url;

    public ApiPublica(String nome, String frequencia, String descricao, Boolean active, String url) {
        this.nome = nome;
        this.frequencia = frequencia;
        this.descricao = descricao;
        this.active = active;
        this.url = url;
    }
}