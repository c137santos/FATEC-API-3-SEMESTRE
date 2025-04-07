package com.group.backend.entity;
import jakarta.persistence.*;
import lombok.Setter;
import lombok.Getter;


@Entity
@Getter
@Setter
@Table(name = "result_tag")
@IdClass(TagResultId.class)
public class TagResult {

    @Id    
    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Id
    @ManyToOne
    @JoinColumn(name = "res_id")
    private ResultApi resId;


    public TagResult(Tag tag, ResultApi resultadoApi) {
        this.tag = tag;
        this.resId = resultadoApi;
    }    
}
