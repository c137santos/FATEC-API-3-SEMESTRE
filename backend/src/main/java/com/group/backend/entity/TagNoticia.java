package com.group.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Setter;
import lombok.Getter;
import jakarta.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "tag_noticia")
@IdClass(TagNoticiaId.class)
public class TagNoticia {

    @Id
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "noti_id")
    private Noticia notiId;

    @Id
    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tagId;
}
