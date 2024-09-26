package com.group.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tag_noticia")
@IdClass(TagNoticiaId.class)
public class TagNoticia {

    @Id
    @ManyToOne
    @JoinColumn(name = "noti_id")
    private Noticia notiId;

    @Id
    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tagId;

    public Noticia getNotiId() {
        return notiId;
    }

    public void setNoticia(Noticia noticia) {
        this.notiId = noticia;
    }

    public Tag getTagId() {
        return tagId;
    }

    public void setTagId(Tag tag) {
        this.tagId = tag;
    }
}