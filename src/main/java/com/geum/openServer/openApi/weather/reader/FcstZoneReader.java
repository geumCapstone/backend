package com.geum.openServer.openApi.weather.reader;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class FcstZoneReader implements ItemReader {

    private final RestTemplate restTemplate;

    @Value("${openapi.weather.fcstzone.url}")
    String url;

    @Override
    public Object read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        /** TODO : 구현 필요 */

        return null;
    }
}
