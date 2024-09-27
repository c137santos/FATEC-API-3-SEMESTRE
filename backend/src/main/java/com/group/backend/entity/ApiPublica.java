package com.group.backend.entity;
import java.time.LocalDate;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "api_publica")
public class ApiPublica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "api_id")
    private Long id;

    @Column(name = "api_nome", nullable = false)
    private String nome;

    @Column(name = "api_frequencia", nullable = false)
    private String frequencia;

    @Column(name = "api_descricao")
    private String descricao;

    @Column(name = "api_active")
    private Boolean active;

    @Column(name = "api_url", length = 2048)
    private String url;
    
    @Column(name = "api_proximo_data_crawl")
    private LocalDate dataFuturaCaptura;

    public ApiPublica() {}

    public ApiPublica(String nome, String frequencia, String descricao, Boolean active, String url, LocalDate dataFuturaCaptura) {
        this.nome = nome;
        this.frequencia = frequencia;
        this.descricao = descricao;
        this.active = active;
        this.url = url;
        this.dataFuturaCaptura = dataFuturaCaptura;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(String frequencia) {
        this.frequencia = frequencia;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDate getDataFuturaCaptura() {
        return dataFuturaCaptura;
    }

    public void setDataFuturaCaptura(LocalDate dataFuturaCaptura) {
        this.dataFuturaCaptura = dataFuturaCaptura;
    }

    
    public ApiPublica() {}

    public ApiPublica(String nome, String frequencia, String descricao, Boolean active, String url, LocalDate dataFuturaCaptura) {
        this.nome = nome;
        this.frequencia = frequencia;
        this.descricao = descricao;
        this.active = active;
        this.url = url;
    }
}