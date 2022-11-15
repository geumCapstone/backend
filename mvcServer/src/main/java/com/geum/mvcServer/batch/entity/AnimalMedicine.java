package com.geum.mvcServer.batch.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "animalMedicine")
public class AnimalMedicine {

    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String shapNm; // 형태

    private String animalOnlyMdcinNmKor; // 의약품 한글명

    private String animalOnlyMdcinNmEng; // 의약품 영문명

    private String systmNm; // 계통명

    private String applcObjAnimal; // 적용 대상 동물

    private String stbly; // 안정성

}
