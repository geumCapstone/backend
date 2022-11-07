package com.geum.openServer.openApi.api.reader;

import com.geum.openServer.openApi.api.entity.AnimalDeseaseInfo;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class AnimalDeseaseInfoReader {

    @Bean
    public FlatFileItemReader<AnimalDeseaseInfo> csvFileItemReader() {
        FlatFileItemReader<AnimalDeseaseInfo> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new ClassPathResource("/csv/animal_desease_2015.csv"));
        flatFileItemReader.setLinesToSkip(1); // 1번 라인 생략
        flatFileItemReader.setEncoding("EUC-KR");

        DefaultLineMapper<AnimalDeseaseInfo> defaultLineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setNames("DISS_NO", "DISS_NM", "ENG_DISS_NM", "INFO_OFFER_NM", "RGSDE", "MAIN_INFC_ANIMAL", "CAUSE_CMMN_CL");
        delimitedLineTokenizer.setStrict(true); // 컬럼 일치여부(true)

        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);

        BeanWrapperFieldSetMapper<AnimalDeseaseInfo> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(AnimalDeseaseInfo.class);

        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

        flatFileItemReader.setLineMapper(defaultLineMapper);

        return flatFileItemReader;
    }
}
