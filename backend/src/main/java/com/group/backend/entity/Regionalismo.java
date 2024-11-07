package com.group.backend.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "regionalismo")
public class Regionalismo {
    
    @Id    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reg_id")
    private Long regId;

    @Column(name = "tag_id")
    private Integer tagId;

    @Column(name = "nome", length = 100)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "tag_id", referencedColumnName = "tag_id", insertable = false, updatable = false)
    @JsonBackReference
    private Tag tag;

    // Getters e Setters
    public Long getRegId() {
        return regId;
    }

    public void setRegId(Long regId) {
        this.regId = regId;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
