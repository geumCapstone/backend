package com.geum.openServer.openApi.weather.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Getter @Setter
public class FcstZoneDTO {

    @Id @Column(unique = true)
    private Long id;

    private String regName; // 예보구역 지역명

    private double lat; // 위도
    private double lon; // 경도
    private double ht; // 해발고도

    private String tmSt; // 시작시간
    private String tmEd; // 종료시간
}
