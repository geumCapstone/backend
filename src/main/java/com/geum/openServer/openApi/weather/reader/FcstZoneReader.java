package com.geum.openServer.openApi.weather.reader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geum.openServer.openApi.weather.dto.FcstZoneDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
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

    /* @Value("${openapi.weather.fcstzone.url}")
    String url = "http://apis.data.go.kr/1360000/FcstZoneInfoService/getFcstZoneCd?pageNo=1&numOfRows=1000&dataType=JSON&serviceKey=9Dpca3K4xBbHh6k4Plv6dqURM1zCW+AKzjvw9LYt3Omp6JzusRMkwrM/QH/viN8MH+x06MmqekglEqIy0vRRmQ==";
    */

    StringBuilder url = new StringBuilder("http://apis.data.go.kr/1360000/FcstZoneInfoService/getFcstZoneCd?pageNo=1&numOfRows=1000&dataType=JSON&serviceKey=");


    @Override
    public Object read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        log.info("FcstZoneReader -> read method execute");

        return fetchData();
    }

    private List<FcstZoneDTO> fetchData() throws JsonProcessingException {
        httpHeaders.set("Accept", "application/json");

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(url.toString());
        log.info("FcstZone 데이터 읽는 중: {}", uriComponentsBuilder.toUriString());

        ResponseEntity<String> response = restTemplate.exchange(uriComponentsBuilder.toUriString(), HttpMethod.GET, new HttpEntity<>(httpHeaders), String.class);
        /** 수정 필요라인 */
        Map<String, Object> responseObject = objectMapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>() {});
        Map<String, Object> responseProperty = (Map<String, Object>) responseObject.get("response");
        Map<String, Object> bodyProperty = (Map<String, Object>) responseProperty.get("body");
        Map<String, Object> itemsProperty = (Map<String, Object>) bodyProperty.get("items");

        FcstZoneDTO[] fcstZoneData = objectMapper.readValue(objectMapper.writeValueAsString(itemsProperty.get("item")), FcstZoneDTO[].class);
        // HashMap<String, Object> itemObject = objectMapper.readValue(response.getBody(), HashMap.class);

        log.info("data: {}", fcstZoneData);

        return Arrays.asList(fcstZoneData);
    }

}
