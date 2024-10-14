package com.group.backend.entity;
import java.time.LocalDate;

import lombok.NoArgsConstructor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Table(name = "tag")
public class Tag {

    @Id    
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @Column(name = "tag_id")
    @Getter
    @Setter
    private Long tagId;

    @Column(name = "tag_nome", nullable = false, length = 30)
    @Getter
    @Setter
    private String tagNome;

    @Column(name = "tag_descricao", length = 250)
    @Getter
    @Setter
    private String tagDescricao;

    @Column(name = "tag_data")
    @Getter
    @Setter
    private LocalDate tagData;

    @Column(name = "tag_active")
    @Getter
    @Setter
    private boolean tagActive;
}
