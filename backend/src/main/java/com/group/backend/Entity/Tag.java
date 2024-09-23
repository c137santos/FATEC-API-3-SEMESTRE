package com.group.backend.Entity;


import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    @Column(name = "tag_nome", nullable = false)
    private String nome;

    @Column(name = "tag_descricao")
    private String descricao;

    @Column(name = "tag_data")
    private Date data;

    @Column(name = "tag_active")
    private Boolean active;

    // Getters and setters
}
