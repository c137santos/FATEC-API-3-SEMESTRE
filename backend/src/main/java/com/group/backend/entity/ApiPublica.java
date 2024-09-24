package com.group.backend.entity;
import jakarta.persistence.*;
import lombok.Setter;
import lombok.Getter;

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

    @Column(name = "descricao", columnDefinition = "TEXT")
    @Setter
    private String descricao;

    @Column(name = "api_active")
    @Setter
    private Boolean active;

    @Column(name = "api_url", length = 2048)
    @Setter
    @Getter
    private String url;
    
    @Override
    public String toString() {
        return "ApiPublica{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", frequencia='" + frequencia + '\'' +
                ", descricao='" + descricao + '\'' +
                ", active=" + active +
                ", url='" + url + '\'' +
                '}';
    }
    // getters and setters

    // constructors

    // other methods
}



// CREATE TABLE public.api_publica (
//     api_id SERIAL PRIMARY KEY,
//     api_nome VARCHAR(255) NOT NULL,
//     api_frequencia VARCHAR(255) NOT NULL,
//     api_proximo_data_crawl DATE,
//     descricao TEXT,
//     api_active BOOLEAN,
//     api_url VARCHAR(2048)
// );
