package com.geum.mvcServer.apis.batch.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "fcstZone")
public class FcstZone {

    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String regName; // 예보구역 지역명

    private double lat; // 위도
    private double lon; // 경도
    // private double ht; // 해발고도

    private String tmSt; // 시작시간
    private String tmEd; // 종료시간

}
