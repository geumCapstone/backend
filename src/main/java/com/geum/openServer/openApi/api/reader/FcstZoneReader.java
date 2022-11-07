package com.geum.openServer.openApi.api.reader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geum.openServer.openApi.api.dto.FcstZoneDTO;
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
public class FcstZoneReader implements ItemReader {

    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders;
    private final ObjectMapper objectMapper;
    private List<FcstZoneDTO> fcstZoneData;

    private int currentPage = 1;
    private int totalPage;
    private int numOfRows = 1000;
    private int nextIndex = 0;


    @Override
    public Object read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        log.info("FcstZoneReader의 read 메소드가 실행중입니다.");

        if(fcstZoneDataIsNotInitialized()) {
            // 초기데이터 없으면 호출함
            fcstZoneData = fetchData(currentPage, numOfRows);
        }

        FcstZoneDTO nextData = null;

        if (nextIndex < fcstZoneData.size()) {
            nextData = fcstZoneData.get(nextIndex);
            nextIndex += 1;

            if (nextIndex == fcstZoneData.size()) {
                if (totalPage <= currentPage * numOfRows) return null;

                currentPage += 1;
                nextIndex = 0;
                nextData = null;
            }
        }
        log.info("Found data: {}", nextData);

        return nextData;
    }

    private boolean fcstZoneDataIsNotInitialized() {
        return this.fcstZoneData == null;
    }

    private List<FcstZoneDTO> fetchData(int currentPage, int numOfRows) throws JsonProcessingException {
        // 헤더 설정
        httpHeaders.set("Accept", "application/json");
        // 접속 주소 설정
        UriComponents uriComponents = UriComponentsBuilder
                .fromUriString("http://apis.data.go.kr/1360000/FcstZoneInfoService/getFcstZoneCd?serviceKey=MS9S6L93rOOZBSjA6GRYEPCnIefMccC5H4zB/xLQC3u5ZEdjMWQCr7lA7F3YB2WBxU6SON/YiuYL48i6O4IrIg==")
                .queryParam("numOfRows", numOfRows)
                .queryParam("pageNo", currentPage)
                .queryParam("dataType", "JSON")
                .build();
        uriComponents.encode();
        log.info("FcstZone 데이터 읽는 중: {}", uriComponents.toUriString());

        ResponseEntity<String> response = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, new HttpEntity<>(httpHeaders), String.class);

        /** 수정 필요라인 */
        Map<String, Object> responseObject = objectMapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>() {});
        Map<String, Object> responseProperty = (Map<String, Object>) responseObject.get("response");
        Map<String, Object> bodyProperty = (Map<String, Object>) responseProperty.get("body");
        Map<String, Object> itemsProperty = (Map<String, Object>) bodyProperty.get("items");

        FcstZoneDTO[] fcstZoneData = objectMapper.readValue(objectMapper.writeValueAsString(itemsProperty.get("item")), FcstZoneDTO[].class);
        totalPage = Integer.parseInt(bodyProperty.get("totalCount").toString());

        log.info("data: {}", fcstZoneData);

        return Arrays.asList(fcstZoneData);
    }

}
