package com.geum.openServer.openApi.weather.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data @Entity
@NoArgsConstructor
@Getter @Setter
public class HealthColdIdx {

    @Id @Column(unique = true)
    private Long id;

    /** TODO : 구현 필요 */
}
