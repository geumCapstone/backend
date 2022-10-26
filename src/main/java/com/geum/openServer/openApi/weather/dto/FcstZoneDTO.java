package com.geum.openServer.openApi.weather.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter @Setter
public class FcstZoneDTO {

    private String regId;
    private double lat;
    private double lon;
    private String regEn;
    private String regName;
    private String regSp;
    private String regUp;
    private long seq;
    private long stnF3;
    private String tmEd;
    private String tmSt;

}
