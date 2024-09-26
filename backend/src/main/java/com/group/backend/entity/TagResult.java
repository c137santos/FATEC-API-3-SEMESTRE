package com.group.backend.entity;
import jakarta.persistence.*;



@Entity
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

    public Tag getTag() {
        return tag;
    }

    public ResultApi getResult() {
        return resId;
    }

    public void setResult(ResultApi resultadoApi) {
        this.resId = resultadoApi;
    }


    public void setTag(Tag resultadoApi) {
        this.tag = resultadoApi;
    }
    
}
