package com.group.backend.entity;

import java.util.Date;

import org.hibernate.annotations.Type;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;

@Entity
@Table(name = "result_api")
public class ResultApi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "res_id")
    private Long resId;

    @Column(name = "res_data")
    @Setter
    private Date resData;

    @Column(name = "res_payload", columnDefinition = "jsonb")
    @Setter
    @Getter
    @Type(JsonBinaryType.class)
    private String resPayload;

    @ManyToOne
    @JoinColumn(name = "api_id")
    private ApiPublica apiPublica;

    public void setApiPublica (ApiPublica apiPublica) {
        this.apiPublica = apiPublica;
    }
}
