package com.geum.openServer.openApi.weather.reader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geum.openServer.openApi.weather.dto.AnimalMedicineDTO;
import com.geum.openServer.openApi.weather.dto.FcstZoneDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class AnimalMedicineReader implements ItemReader {

    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders;
    private final ObjectMapper objectMapper;
    private List<AnimalMedicineDTO> animalMedicineData;

    private int currentPage = 1;
    private int totalPage;
    private int numOfRows = 1000;
    private int nextIndex = 0;

    @Override
    public Object read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        log.info("AnimalMedicine read 메소드가 실행중입니다.");

        if(animalMedicineDataIsNotInitialized()) {
            // 초기데이터 없으면 호출함
            animalMedicineData = fetchData(currentPage, numOfRows);
        }

        AnimalMedicineDTO nextData = null;

        if (nextIndex < animalMedicineData.size()) {
            nextData = animalMedicineData.get(nextIndex);
            nextIndex += 1;

            if (nextIndex == animalMedicineData.size()) {
                if (totalPage <= currentPage * numOfRows) return null;

                currentPage += 1;
                nextIndex = 0;
                nextData = null;
            }
        }
        log.info("Found data: {}", nextData);

        return nextData;
    }

    private boolean animalMedicineDataIsNotInitialized() {
        return this.animalMedicineData == null;
    }

    private List<AnimalMedicineDTO> fetchData(int currentPage, int numOfRows) throws JsonProcessingException {
        // 헤더 설정
        httpHeaders.set("Accept", "application/json");
        // 접속 주소 설정
        UriComponents uriComponents = UriComponentsBuilder
                .fromUriString("https://openapi.foodsafetykorea.go.kr/api/a708c7460d134aad9c75/I1070/json/1/1000")
                .build();
        uriComponents.encode();
        log.info("AnimalMedicine 데이터 읽는 중: {}", uriComponents.toUriString());

        ResponseEntity<String> response = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, new HttpEntity<>(httpHeaders), String.class);

        /** 수정 필요라인 */
        Map<String, Object> responseObject = objectMapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>() {});
        Map<String, Object> responseProperty = (Map<String, Object>) responseObject.get("I1070");

        AnimalMedicineDTO[] animalMedicineData = objectMapper.readValue(objectMapper.writeValueAsString(responseProperty.get("row")), AnimalMedicineDTO[].class);
        totalPage = Integer.parseInt(responseProperty.get("total_count").toString());

        log.info("data: {}", animalMedicineData);

        return Arrays.asList(animalMedicineData);
    }

}
