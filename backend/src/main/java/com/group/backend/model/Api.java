    package com.group.backend.model;

    import javax.persistence.*;
    import java.time.LocalDate;

    @Entity
    public class Api {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String nome;

        @Column(nullable = false)
        private String url;

        @Column(nullable = false)
        private String periodoCaptura;

        @Column(nullable = false)
        private LocalDate dataFuturaCaptura;

        // Getters and Setters
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

        public String getPeriodoCaptura() {
            return periodoCaptura;
        }

        public void setPeriodoCaptura(String periodoCaptura) {
            this.periodoCaptura = periodoCaptura;
        }

        public LocalDate getDataFuturaCaptura() {
            return dataFuturaCaptura;
        }

        public void setDataFuturaCaptura(LocalDate dataFuturaCaptura) {
            this.dataFuturaCaptura = dataFuturaCaptura;
        }
    }