package com.group.backend.entity;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "portal")
@NoArgsConstructor
@AllArgsConstructor
public class Portal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "por_id")
    private Long id;

    @Column(name = "por_ativo")
    private boolean ativo;

    @Column(name = "por_nome", length = 30)
    private String nome;

    @Column(name = "por_url", length = 2048)
    private String url;

    @Column(name = "por_data")
    private LocalDate data;

    @Column(name = "por_frequencia", length = 30)
    private String frequencia;

    // Relacionamento com TagPortal
    @OneToMany(mappedBy = "portal", cascade = CascadeType.ALL)
    private List<TagPortal> tagPortals; // Associação com TagPortal
}