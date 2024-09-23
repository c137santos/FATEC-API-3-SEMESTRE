package com.group.backend.entity;
import jakarta.persistence.*;


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

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "api_active")
    private Boolean active;

    @Column(name = "api_url", length = 2048)
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

    private String api_nome;
    private String api_frequencia;
    private String api_descricao;
    private boolean api_active;
    private String api_url;


    public String getApiNome() {
        return api_nome;
    }

    public void setApiNome(String api_nome) {
        this.api_nome = api_nome;
    }

    public String getApiFrequencia() {
        return api_frequencia;
    }

    public void setApiFrequencia(String api_frequencia) {
        this.api_frequencia = api_frequencia;
    }

    public String getApiDescricao() {
        return api_descricao;
    }

    public void setApiDescricao(String api_descricao) {
        this.api_descricao = api_descricao;
    }

    public boolean getApiActive() {
        return api_active;
    }

    public void setApiActive(boolean api_active) {
        this.api_active = api_active;
    }

    public String getApiUrl() {
        return api_url;
    }

    public void setApiUrl(String api_url) {
        this.api_url = api_url;
    }
}

    // constructors

    // other methods




// CREATE TABLE public.api_publica (
//     api_id SERIAL PRIMARY KEY,
//     api_nome VARCHAR(255) NOT NULL,
//     api_frequencia VARCHAR(255) NOT NULL,
//     api_proximo_data_crawl DATE,
//     descricao TEXT,
//     api_active BOOLEAN,
//     api_url VARCHAR(2048)
// );
