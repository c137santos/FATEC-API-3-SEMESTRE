package com.group.backend.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;


import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class TagNoticiaId implements Serializable {

    private Long tagId;
    private Long notiId;

    public TagNoticiaId() {}

    public TagNoticiaId(Long tagId, Long notiId) {
        this.tagId = tagId;
        this.notiId = notiId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TagNoticiaId)) return false;
        TagNoticiaId that = (TagNoticiaId) o;
        return Objects.equals(tagId, that.tagId) && Objects.equals(notiId, that.notiId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagId, notiId);
    }
}
