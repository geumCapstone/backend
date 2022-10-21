package com.geum.openServer.openApi.weather.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Getter @Setter
@Table(name = "fcstZone")
public class FcstZone {

    @Id @Column(unique = true)
    private Long id;

    private String regName; // 예보구역 지역명

    private double lat; // 위도
    private double lon; // 경도
    private double ht; // 해발고도

    private String tmSt; // 시작시간
    private String tmEd; // 종료시간

    @Builder
    public FcstZone(String regName, double lat, double lon, double ht, String tmSt, String tmEd) {
        this.regName = regName;
        this.lat = lat;
        this.lon = lon;
        this.ht = ht;
        this.tmSt = tmSt;
        this.tmEd = tmEd;
    }
}
