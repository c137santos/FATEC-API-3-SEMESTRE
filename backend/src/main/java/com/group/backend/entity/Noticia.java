package com.group.backend.entity;
import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "noticia")
public class Noticia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "noti_id")
    private Long notiId;

    @Column(name = "noti_data")
    private LocalDate notiData;

    @Column(name = "noti_text")
    private String notiText;

    @Column(name = "noti_url", length = 2048)
    private String url;

    @Column(name = "por_id")
    private Integer porId;

    @ManyToOne
    @JoinColumn(name = "por_id", referencedColumnName = "por_id", insertable = false, updatable = false)
    private Portal portal;

    @Column(name = "rep_id")
    private Integer resId;

    @ManyToOne
    @JoinColumn(name = "rep_id", referencedColumnName = "rep_id", insertable = false, updatable = false)
    private Reporter reporte;

}
