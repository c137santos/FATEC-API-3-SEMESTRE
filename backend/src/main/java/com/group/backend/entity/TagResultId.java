package com.group.backend.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class TagResultId implements Serializable {

    private Long tag;
    private Long resId;

    public TagResultId() {}

    public TagResultId(Long tag, Long resId) {
        this.tag = tag;
        this.resId = resId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TagResultId)) return false;
        TagResultId that = (TagResultId) o;
        return Objects.equals(tag, that.tag) && Objects.equals(resId, that.resId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag, resId);
    }
}
