package com.geum.openServer.openApi.weather.writer;

import com.geum.openServer.openApi.weather.dto.FcstZoneDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@Slf4j
public class FcstZoneWriter implements ItemWriter<FcstZoneDTO> {

    @Override
    public void write(List<? extends FcstZoneDTO> items) throws Exception {
        log.info("FcstZone 데이터 생성: {}", items);
    }
}
