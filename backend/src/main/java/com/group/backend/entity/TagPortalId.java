package com.group.backend.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class TagPortalId implements Serializable {

    private Long tag;
    private Long portal;

    public TagPortalId() {}

    public TagPortalId(Long tag, Long portal) {
        this.tag = tag;
        this.portal = portal;
    }

    public Long getTag() {
        return tag;
    }

    public void setTag(Long tag) {
        this.tag = tag;
    }

    public Long getPortal() {
        return portal;
    }

    public void setPortal(Long portal) {
        this.portal = portal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagPortalId that = (TagPortalId) o;
        return Objects.equals(tag, that.tag) && Objects.equals(portal, that.portal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag, portal);
    }
}