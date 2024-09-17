package com.group.backend.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@Embeddable
public class TagNoticiaId implements Serializable {

        private Long tagId;
        private Long notiId;

        public TagNoticiaId() {};

        public Long getTagId() {
            return tagId;
        }

        public void setTagId(Long tag) {
            this.tagId = tag;
        }

        public Long getNotiId() {
            return notiId;
        };

        public void setNotiId(Long noticia) {
            this.notiId = noticia;
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TagNoticiaId that = (TagNoticiaId) o;
            return Objects.equals(tagId, that.tagId) && Objects.equals(notiId, that.notiId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(tagId, notiId);
        }
}
