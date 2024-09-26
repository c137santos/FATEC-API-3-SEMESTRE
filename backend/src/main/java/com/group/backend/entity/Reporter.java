package com.group.backend.entity;
import jakarta.persistence.*;


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
    
    public Long getId() {
        return repId;
    }
    
    public void setId(Long id) {
        this.repId = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public Portal getPortal() {
        return portal;
    }
    
    public void setPortal(Portal portal) {
        this.portal = portal;
    }
    
}
