package com.group.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tag_portal")
@IdClass(TagPortalId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagPortal {

    @Id
    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Id
    @ManyToOne
    @JoinColumn(name = "por_id")
    @JsonBackReference
    private Portal portal;
}