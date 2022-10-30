package com.geum.openServer.openApi.weather.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter @Setter
public class AnimalMedicineDTO {

    @JsonProperty("SHAP_NM")
    private String shapNm;

    @JsonProperty("ANIMAL_ONLY_MDCIN_NM_KOR")
    private String animalOnlyMdcinNmKor;

    @JsonProperty("CAS_NM")
    private String casNm;

    @JsonProperty("IUPAC_NM")
    private String iupacNm;

    @JsonProperty("PKA")
    private String pka;

    @JsonProperty("SYSTM_NM")
    private String systmNm;

    @JsonProperty("SOLUB")
    private String solub;

    @JsonProperty("POIOF")
    private String poiof;

    @JsonProperty("APPLC_OBJ_ANIMAL")
    private String applcObjAnimal;

    @JsonProperty("BOILPNT")
    private String boilpnt;

    @JsonProperty("DENS_UNIT")
    private String densUnit;

    @JsonProperty("ANIMAL_ONLY_MDCIN_NM_ENG")
    private String animalOnlyMdcinNmEng;

    @JsonProperty("MCFRL")
    private String mcfrl;

    @JsonProperty("STBLY")
    private String stbly;

    @JsonProperty("MCWGH")
    private String mcwgh;

    @JsonProperty("STEPR")
    private String stepr;

    @JsonProperty("LOGPOW")
    private String logpow;

}
