package com.geum.openServer.openApi.batch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geum.openServer.openApi.weather.dto.AnimalMedicineDTO;
import com.geum.openServer.openApi.weather.dto.FcstZoneDTO;
import com.geum.openServer.openApi.weather.entity.AnimalDeseaseInfo;
import com.geum.openServer.openApi.weather.entity.AnimalMedicine;
import com.geum.openServer.openApi.weather.entity.FcstZone;
import com.geum.openServer.openApi.weather.reader.AnimalDeseaseInfoReader;
import com.geum.openServer.openApi.weather.reader.AnimalMedicineReader;
import com.geum.openServer.openApi.weather.reader.FcstZoneReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManagerFactory;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class BatchConfiguration {

    /** Batch Object */
    private final EntityManagerFactory entityManagerFactory;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    /** Reader Object */
    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders;
    private final ObjectMapper objectMapper;

    /** ETC */
    private final AnimalDeseaseInfoReader animalDeseaseInfoReader;

    /** Job */
    @Bean
    public Job fcstZoneJob() {
        log.info("BatchConfiguration -> fcstZoneJob 실행");
        // Job 실행
        return jobBuilderFactory.get("fcstZoneJob")
                .start(fcstZoneStep())
                .on("FAILED").fail()
                .end()
                .build();
    }

    @Bean
    public Job animalMedicineJob() {
        log.info("BatchConfiguration -> animalMedicineJob 실행");
        return jobBuilderFactory.get("animalMedicineJob")
                .start(animalMedicineStep())
                .on("FAILED").fail()
                .end()
                .build();
    }

    @Bean
    public Job animalDeseaseInfoJob() {
        log.info("BatchConfiguration -> animalDeseaseInfoJob 실행");
        return jobBuilderFactory.get("animalDeseaseInfoJob")
                .start(animalDeseaseInfoStep())
                .on("FAILED").fail()
                .end()
                .build();
    }

    /** Step */
    @Bean
    public Step fcstZoneStep() {
        log.info("BatchConfiguration -> fcstZoneStep");
        return stepBuilderFactory.get("fcstZoneStep")
                .<FcstZoneDTO, FcstZone>chunk(1000)
                .reader(fcstZoneItemReader())
                .processor(fcstZoneItemProcessor())
                .writer(fcstZoneJpaItemWriter())
                .build();
    }

    @Bean
    public Step animalMedicineStep() {
        log.info("BatchConfiguration -> animalMedicineStep");
        return stepBuilderFactory.get("animalMedicineStep")
                .<AnimalMedicineDTO, AnimalMedicine>chunk(1000)
                .reader(animalMedicineItemReader())
                .processor(animalMedicineItemProcessor())
                .writer(animalMedicineJpaItemWriter())
                .build();
    }

    @Bean
    public Step animalDeseaseInfoStep() {
        log.info("BatchConfiguration -> animalDeseaseInfoStep");
        return stepBuilderFactory.get("animalDeseaseInfoStep")
                .<AnimalDeseaseInfo, AnimalDeseaseInfo>chunk(1000)
                .reader(animalDeseaseInfoReader.csvFileItemReader())
                .writer(animalDeseaseInfoJpaItemWriter())
                .build();
    }

    /** Reader */
    public ItemReader<FcstZoneDTO> fcstZoneItemReader() {
        return new FcstZoneReader(restTemplate, httpHeaders, objectMapper);
    }

    public ItemReader<AnimalMedicineDTO> animalMedicineItemReader() {
        return new AnimalMedicineReader(restTemplate, httpHeaders, objectMapper);
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

    public ItemProcessor<AnimalMedicineDTO, AnimalMedicine> animalMedicineItemProcessor() {
        return AnimalMedicineDTO -> AnimalMedicine.builder()
                .shapNm(AnimalMedicineDTO.getShapNm())
                .animalOnlyMdcinNmKor(AnimalMedicineDTO.getAnimalOnlyMdcinNmKor())
                .animalOnlyMdcinNmEng(AnimalMedicineDTO.getAnimalOnlyMdcinNmEng())
                .systmNm(AnimalMedicineDTO.getSystmNm())
                .applcObjAnimal(AnimalMedicineDTO.getApplcObjAnimal())
                .stbly(AnimalMedicineDTO.getStbly())
                .build();
    }

    /** Writer */
    public JpaItemWriter<FcstZone> fcstZoneJpaItemWriter() {
        JpaItemWriter<FcstZone> itemWriter = new JpaItemWriter<>();
        itemWriter.setEntityManagerFactory(entityManagerFactory);
        return itemWriter;
    }

    public JpaItemWriter<AnimalMedicine> animalMedicineJpaItemWriter() {
        JpaItemWriter<AnimalMedicine> itemWriter = new JpaItemWriter<>();
        itemWriter.setEntityManagerFactory(entityManagerFactory);
        return itemWriter;
    }

    public JpaItemWriter<AnimalDeseaseInfo> animalDeseaseInfoJpaItemWriter() {
        JpaItemWriter<AnimalDeseaseInfo> itemWriter = new JpaItemWriter<>();
        itemWriter.setEntityManagerFactory(entityManagerFactory);
        return itemWriter;
    }
}
