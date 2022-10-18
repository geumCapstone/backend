package com.geum.openServer.openApi.config.batch;

import com.geum.openServer.openApi.weather.dto.FcstZoneDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Configuration
public class BatchConfiguration {

    /** Spring Batch 기능을 활용하기 위해 선언 */
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public ItemReader<FcstZoneDTO> fcstZoneDTOItemReader(Environment environment, RestTemplate restTemplate) {

        /** TODO: 구현 필요 */

        return null;
    }

}
