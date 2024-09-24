package com.group.backend.entity;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;



@Entity
@Table(name = "tag")
public class Tag {

    @Id    
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @Column(name = "tag_id")
    private Long tagId;

    @Column(name = "tag_nome", nullable = false, length = 30)
    private String tagNome;

    @Column(name = "tag_descricao", length = 250)
    private String tagDescricao;

    @Column(name = "tag_data")
    private Date tagData;

    @Column(name = "tag_active")
    private boolean tagActive;

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getTagNome() {
        return tagNome;
    }

    public void setTagNome(String tagNome) {
        this.tagNome = tagNome;
    }

    public String getTagDescricao() {
        return tagDescricao;
    }

    public void setTagDescricao(String tagDescricao) {
        this.tagDescricao = tagDescricao;
    }

    public Date getTagData() {
        return tagData;
    }

    public void setTagData(Date tagData) {
        this.tagData = tagData;
    }

    public boolean isTagActive() {
        return tagActive;
    }

    public void setTagActive(boolean tagActive) {
        this.tagActive = tagActive;
    }

    
}
