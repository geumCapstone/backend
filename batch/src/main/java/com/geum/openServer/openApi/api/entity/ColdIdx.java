package com.geum.openServer.openApi.api.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter @Setter
@Table(name = "coldIdx")
public class ColdIdx {

    @Id @Column(unique = true) @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String areaNo;

    private String date_time;

    private String today; // 오늘 예측값

    private String tomorrow; // 내일 예측값

    private String dayaftertomorrow; // 모레 예측값

    private String twodaysaftertomorrow; // 글피 예측값

    @Builder
    public ColdIdx(String date_time, String areaNo, String today, String tomorrow, String dayaftertomorrow, String twodaysaftertomorrow) {
        this.date_time = date_time;
        this.areaNo = areaNo;
        this.today = today;
        this.tomorrow = tomorrow;
        this.dayaftertomorrow = dayaftertomorrow;
        this.twodaysaftertomorrow = twodaysaftertomorrow;
    }

}
