package com.group.backend.entity;
import jakarta.persistence.*;
import java.util.Date;



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

    
}
