package com.group.backend.entity;
import jakarta.persistence.*;
import lombok.Setter;
import lombok.Getter;
import java.time.LocalDate;

@Entity
@Table(name = "api_publica")
public class ApiPublica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "api_id")
    @Getter
    private Long id;

    @Column(name = "api_nome", nullable = false)
    @Setter
    @Getter
    private String nome;

    @Column(name = "api_frequencia", nullable = false)
    @Setter
    private String frequencia;

    @Column(name = "api_descricao")
    private String descricao;

    @Column(name = "api_active")
    @Setter
    private Boolean active;

    @Column(name = "api_url", length = 2048)
    @Setter
    @Getter
    private String url;
    
    public ApiPublica() {}

    public ApiPublica(String nome, String frequencia, String descricao, Boolean active, String url) {
        this.nome = nome;
        this.frequencia = frequencia;
        this.descricao = descricao;
        this.active = active;
        this.url = url;
    }
}