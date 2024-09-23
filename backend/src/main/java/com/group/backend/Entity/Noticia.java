package com.group.backend.Entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "noticia")
public class Noticia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "noti_id")
    private Long id;

    @Column(name = "noti_data")
    private Date data;

    @Column(name = "noti_text")
    private String texto;

    @ManyToOne
    @JoinColumn(name = "por_id", referencedColumnName = "por_id")
    private Portal portal;

    // Getters and setters
}
