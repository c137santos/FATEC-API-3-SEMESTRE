package com.group.backend.Entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Embeddable
public class TagPortalId implements Serializable {

    @Column(name = "tag_id")
    private Long tagId;

    @Column(name = "por_id")
    private Long porId;

    // Default constructor, getters, setters, equals, and hashCode
}
