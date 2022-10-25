package com.geum.openServer.openApi.weather.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FcstZoneRepository extends JpaRepository<FcstZone, Long> {

    /** 전체 데이터 수집, id는 PK이므로 NULL일 수 없음 ! */
    Optional<List<FcstZone>> findAllByIdNotNull();

}
