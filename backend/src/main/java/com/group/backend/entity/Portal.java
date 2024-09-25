package com.group.backend.entity;
import java.util.Date;

import jakarta.persistence.*;
@Entity
@Table(name = "portal")
public class Portal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "por_id")
    private Long id;

    @Column(name = "por_nome", length = 30)
    private String nome;

    @Column(name = "por_url", length = 2048)
    private String url;

    @Column(name = "por_data")
    private Date data;

    @Column(name = "por_frequencia", length = 30)
    private String frequencia;

    // Construtores, getters e setters

    public Portal() {}

    public Portal(String nome, String url, Date data, String frequencia) {
        this.nome = nome;
        this.url = url;
        this.data = data;
        this.frequencia = frequencia;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(String frequencia) {
        this.frequencia = frequencia;
    }
}