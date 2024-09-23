package com.group.backend.Entity;


import jakarta.persistence.*;

@Entity
@Table(name = "tag_noticia")
public class TagNoticia {

    @EmbeddedId
    private TagNoticiaId id;

    @ManyToOne
    @MapsId("notiId")
    @JoinColumn(name = "noti_id")
    private Noticia noticia;

    @ManyToOne
    @MapsId("tagId")
    @JoinColumn(name = "tag_id")
    private Tag tag;

    // Getters and setters
}
