package com.group.backend.entity;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "portal")
public class Portal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "por_id")
    @Getter
    @Setter
    private Long id;

    @Column(name = "por_nome", length = 30)
    @Getter
    @Setter
    private String nome;

    @Column(name = "por_url", length = 2048)
    @Getter
    @Setter
    private String url;

    @Column(name = "por_data")
    @Getter
    @Setter
    private LocalDate data;

    @Column(name = "por_frequencia", length = 30)
    @Getter
    @Setter
    private String frequencia;

}