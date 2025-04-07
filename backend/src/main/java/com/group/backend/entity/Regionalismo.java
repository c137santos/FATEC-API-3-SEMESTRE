package com.group.backend.entity;
import lombok.Setter;
import lombok.Getter;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Getter
@Setter
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
}
