package com.geum.openServer.openApi.weather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FcstZoneDTO {

    private String regName; // 예보구역 지역명

    private double lat; // 위도
    private double lon; // 경도
    private double ht; // 해발고도

    private String tmSt; // 시작시간
    private String tmEd; // 종료시간
}
