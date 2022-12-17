package com.geum.mvcServer.apis.batch.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@RequiredArgsConstructor
@Getter
@Setter
@Entity
public class AnimalDeseaseInfo {

    @Id
    @Column(unique = true) @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long DISS_NO;

    private String DISS_NM;

    private String ENG_DISS_MM;

    private String INFO_OFFER_MM;

    private String RGSDE;

    private String MAIN_INFC_ANIMAL;

    private String CAUSE_CMMM_CL;

}
