package com.geum.openServer.openApi.api.reader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geum.openServer.openApi.api.dto.ColdIdxDTO;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class ColdIdxReader implements ItemReader {

    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders;
    private final ObjectMapper objectMapper;

    private List<ColdIdxDTO> coldIdxData;
    private int time = Integer.parseInt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHH"))) - 1;
    private String[] areaCode = new String[] {
            "1100000000",
            "2600000000",
            "2700000000",
            "2800000000",
            "2900000000",
            "3000000000",
            "3100000000",
            "3600000000",
            "4100000000",
            "4200000000",
            "4300000000",
            "4400000000",
            "4500000000",
            "4600000000",
            "4700000000",
            "4800000000",
            "5000000000",
            "5019000000"};
    private int currentPage = 1;
    private int totalPage = 0;
    private int numOfRows = 1000;

    @Override
    public Object read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        ColdIdxDTO nextData = null;

        if (totalPage != 18) {
            coldIdxData = fetchData(currentPage, numOfRows);
            nextData = coldIdxData.get(0);
            totalPage += 1;
        } else {
            nextData = null;
            return null;
        }

        log.info("Found data: {}", nextData);

        return nextData;
    }

    private boolean coldIdxDataIsNotInitialized() {
        return this.coldIdxData == null;
    }

    private List<ColdIdxDTO> fetchData(int currentPage, int numOfRows) throws JsonProcessingException {
        // 헤더 설정
        httpHeaders.set("Accept", "application/json");

        // 접속 주소 설정
        UriComponents uriComponents = UriComponentsBuilder
                .fromUriString("http://apis.data.go.kr/1360000/HealthWthrIdxServiceV2/getColdIdxV2?serviceKey=MS9S6L93rOOZBSjA6GRYEPCnIefMccC5H4zB%2FxLQC3u5ZEdjMWQCr7lA7F3YB2WBxU6SON%2FYiuYL48i6O4IrIg%3D%3D")
                .queryParam("numOfRows", numOfRows)
                .queryParam("pageNo", currentPage)
                .queryParam("dataType", "JSON")
                .queryParam("areaNo", areaCode[totalPage])
                .queryParam("time", time)
                .build();
        uriComponents.encode();
        log.info("ColdIdx 데이터 읽는 중: {}", uriComponents.toUriString());

        ResponseEntity<String> response = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, new HttpEntity<>(httpHeaders), String.class);

        /** 수정 필요라인 */
        Map<String, Object> responseObject = objectMapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>() {});
        Map<String, Object> responseProperty = (Map<String, Object>) responseObject.get("response");
        Map<String, Object> bodyProperty = (Map<String, Object>) responseProperty.get("body");
        Map<String, Object> itemsProperty = (Map<String, Object>) bodyProperty.get("items");

        ColdIdxDTO[] coldIdxData = objectMapper.readValue(objectMapper.writeValueAsString(itemsProperty.get("item")), ColdIdxDTO[].class);

        log.info("data: {}", coldIdxData);

        return Arrays.asList(coldIdxData);
    }

}
