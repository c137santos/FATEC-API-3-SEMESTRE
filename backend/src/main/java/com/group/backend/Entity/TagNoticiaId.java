package com.group.backend.Entity;


import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TagNoticiaId implements Serializable {

    @Column(name = "noti_id")
    private Long notiId;

    @Column(name = "tag_id")
    private Long tagId;

    // Default constructor, getters, setters, equals, and hashCode
}
