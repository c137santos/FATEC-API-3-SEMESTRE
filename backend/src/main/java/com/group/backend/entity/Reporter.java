package com.group.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "reporter")
public class Reporter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rep_id")
    private Long repId;
    
    @Column(name = "rep_nome", nullable = false)
    private String nome;
    
    @ManyToOne
    @JoinColumn(name = "por_id", referencedColumnName = "por_id")
    private Portal portal;
    
    public Reporter(String nome, Portal portal) {
        this.nome = nome;
        this.portal = portal;
    }
    
    public Reporter() {}
}
