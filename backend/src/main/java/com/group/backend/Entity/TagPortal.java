package com.group.backend.Entity;



import jakarta.persistence.*;

@Entity
@Table(name = "tag_portal")
public class TagPortal {

    @EmbeddedId
    private com.group.backend.Entity.TagPortalId id;

    @ManyToOne
    @MapsId("tagId")
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @ManyToOne
    @MapsId("porId")
    @JoinColumn(name = "por_id")
    private Portal portal;

    // Getters and setters
}
