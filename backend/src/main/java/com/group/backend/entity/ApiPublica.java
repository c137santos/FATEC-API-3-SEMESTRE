package com.group.backend.entity;
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
    
    public ApiPublica() {}

    public ApiPublica(String nome, String frequencia, String descricao, Boolean active, String url, LocalDate dataFuturaCaptura) {
        this.nome = nome;
        this.frequencia = frequencia;
        this.descricao = descricao;
        this.active = active;
        this.url = url;
    }
}