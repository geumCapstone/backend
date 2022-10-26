package com.geum.openServer.openApi.batch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geum.openServer.openApi.weather.dto.FcstZoneDTO;
import com.geum.openServer.openApi.weather.entity.FcstZone;
import com.geum.openServer.openApi.weather.entity.FcstZoneRepository;
import com.geum.openServer.openApi.weather.reader.FcstZoneReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
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
import org.springframework.web.reactive.function.client.WebClient;

import javax.persistence.EntityManagerFactory;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WeatherBatchConfiguration {

    /** Batch Object */
    private final EntityManagerFactory entityManagerFactory;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    /** Reader Object */
    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders;
    private final ObjectMapper objectMapper;

    @Bean
    public Job fcstZoneJob() {
        log.info("WeatherBatchConfiguration -> fcstZoneJob 실행");
        // Job 실행
        return jobBuilderFactory.get("fcstZoneJob")
                .start(fcstZoneStep())
                .on("STARTED").stopAndRestart(fcstZoneStep())
                .on("FAILED").fail()
                .end()
                .build();
    }

    @Bean
    public Step fcstZoneStep() {
        log.info("WeatherBatchConfiguration -> fcstZoneStep");
        // fcstZone Step 구성 및 Job에 올림
        return stepBuilderFactory.get("fcstZoneStep")
                .<FcstZoneDTO, FcstZone>chunk(1000)
                .reader(fcstZoneItemReader())
                .processor(fcstZoneItemProcessor())
                .writer(fcstZoneJpaItemWriter())
                .build();
    }

    /** Reader */
    public ItemReader<FcstZoneDTO> fcstZoneItemReader() {
        return new FcstZoneReader(restTemplate, httpHeaders, objectMapper);
    }

    /** ItemProcessor */
    public ItemProcessor<FcstZoneDTO, FcstZone> fcstZoneItemProcessor() {
        return FcstZoneDTO -> FcstZone.builder()
                .regName(FcstZoneDTO.getRegName())
                .lat(FcstZoneDTO.getLat())
                .lon(FcstZoneDTO.getLon())
                // .ht(FcstZoneDTO.getHt())
                .tmSt(FcstZoneDTO.getTmSt())
                .tmEd(FcstZoneDTO.getTmEd())
                .build();
    }

    /** Writer */
    public JpaItemWriter<FcstZone> fcstZoneJpaItemWriter() {
        JpaItemWriter<FcstZone> itemWriter = new JpaItemWriter<>();
        itemWriter.setEntityManagerFactory(entityManagerFactory);
        return itemWriter;
    }
}
