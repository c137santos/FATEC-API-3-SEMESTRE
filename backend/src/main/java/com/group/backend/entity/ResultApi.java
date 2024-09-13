package com.group.backend.entity;

import java.sql.Date;

import jakarta.persistence.*;


@Entity
@Table(name = "result_api")
public class ResultApi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "res_id")
    private Long resId;

    @Column(name = "api_id")
    private Integer apiId;

    @Column(name = "res_data")
    private Date resData;

    @Column(name = "res_payload", columnDefinition = "json")
    private String resPayload;

    @ManyToOne
    @JoinColumn(name = "api_id", referencedColumnName = "api_id", insertable = false, updatable = false)
    private ApiPublica apiPublica;
}
