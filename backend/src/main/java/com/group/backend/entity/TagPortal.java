package com.group.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tag_portal")
@IdClass(TagPortalId.class)
public class TagPortal {

    @Id
    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Id
    @ManyToOne
    @JoinColumn(name = "por_id")
    private Portal portal;


    public TagPortal() {}

    public TagPortal(Tag tag, Portal portal) {
        this.tag = tag;
        this.portal = portal;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public Portal getPortal() {
        return portal;
    }

    public void setPortal(Portal portal) {
        this.portal = portal;
    }

}