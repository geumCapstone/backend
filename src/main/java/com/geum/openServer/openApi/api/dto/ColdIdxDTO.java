package com.geum.openServer.openApi.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter @Setter
public class ColdIdxDTO {

    private String code;
    private String areaNo;

    @JsonProperty("date")
    private String date_time;

    private String today;
    private String tomorrow;
    private String dayaftertomorrow;
    private String twodaysaftertomorrow;

}
