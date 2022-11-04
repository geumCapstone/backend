package com.geum.openServer.openApi.weather.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@RequiredArgsConstructor
@Getter @Setter
@Entity
public class AnimalDeseaseInfo {    // "DISS_NO","DISS_NM","ENG_DISS_NM","INFO_OFFER_NM","RGSDE","MAIN_INFC_ANIMAL","CAUSE_CMMN_CL"

    @Id @Column(unique = true) @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long DISS_NO;

    private String DISS_NM;

    private String ENG_DISS_MM;

    private String INFO_OFFER_MM;

    private String RGSDE;

    private String MAIN_INFC_ANIMAL;

    private String CAUSE_CMMM_CL;

}

/** 출처 : https://data.mafra.go.kr/opendata/data/indexOpenDataDetail.do?data_id=20220214000000001884 */