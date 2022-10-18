package com.geum.openServer.openApi.weather.domain;

import com.geum.openServer.openApi.weather.dto.FcstZoneDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FcstZoneRepository extends JpaRepository<FcstZoneDTO, Long> {

}
