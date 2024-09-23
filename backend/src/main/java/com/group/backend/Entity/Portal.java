package com.group.backend.Entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "portal")
public class Portal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "por_id")
    private Long id;

    @Column(name = "por_nome")
    private String nome;

    @Column(name = "por_url")
    private String url;

    @Column(name = "por_data")
    private Date data;

    @Column(name = "por_frequencia")
    private String frequencia;

    // Getters and setters
}
