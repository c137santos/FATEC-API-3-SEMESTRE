package com.group.backend.entity;

import java.time.LocalDate;
import java.util.List;

import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

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

    // Relacionamento com Regionalismo
    @OneToMany(mappedBy = "tag")
    @Getter
    @Setter
    private List<Regionalismo> regionalismos;

    public List<Regionalismo> listRegionalismos() {
        return regionalismos;
    }
}
