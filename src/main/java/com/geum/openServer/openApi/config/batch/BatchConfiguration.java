package com.geum.openServer.openApi.config.batch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geum.openServer.openApi.weather.dto.FcstZoneDTO;
import com.geum.openServer.openApi.weather.entity.FcstZone;
import com.geum.openServer.openApi.weather.reader.FcstZoneReader;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManagerFactory;

@RequiredArgsConstructor
@Configuration
public class BatchConfiguration {

    /** Spring Batch 기능을 활용하기 위해 선언 */
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    /** 필요 객체 생성 */
    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders;
    private final ObjectMapper objectMapper;

    @Bean
    public ItemReader<FcstZoneDTO> fcstZoneDTOItemReader() {
        return new FcstZoneReader(restTemplate, httpHeaders, objectMapper);
    }

    /** Batch Progress */

    @Bean
    @JobScope
    public Step collectStep(ItemReader<FcstZoneDTO> reader, JpaItemWriter<FcstZone> writer) {
        return stepBuilderFactory.get("collectStep")
                .<FcstZoneDTO, FcstZone>chunk(10)
                .reader(reader)
                .processor(itemProcessor())
                .writer(writer)
                .build();
    }

    @Bean
    public ItemProcessor<FcstZoneDTO, FcstZone> itemProcessor() {
        return FcstZoneDTO -> FcstZone.builder()
                .regName(FcstZoneDTO.getRegName())
                .lat(FcstZoneDTO.getLat())
                .lon(FcstZoneDTO.getLon())
                .ht(FcstZoneDTO.getHt())
                .tmSt(FcstZoneDTO.getTmSt())
                .tmEd(FcstZoneDTO.getTmEd())
                .build();
    }

    @Bean
    public JpaItemWriter<FcstZone> writer() {
        JpaItemWriter<FcstZone> itemWriter = new JpaItemWriter<>();
        itemWriter.setEntityManagerFactory(entityManagerFactory);
        return itemWriter;
    }
}
