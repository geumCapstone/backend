package com.geum.openServer.openApi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;

@Configuration
public class AppConfig {


    /** JSON 응답일 경우 Jackson 설정을 해줘야 함.
     *  따라서 AppConfig 내 Bean 설정으로 하나의 객체만 생성하여 사용하게끔 함. */

    @Bean
    public Jackson2JsonEncoder jackson2JsonEncoder() {
        Jackson2JsonEncoder jackson2JsonEncoder = new Jackson2JsonEncoder(new ObjectMapper(),
                MediaType.APPLICATION_JSON);

        return jackson2JsonEncoder;
    }

    @Bean
    public Jackson2JsonDecoder jackson2JsonDecoder() {
        Jackson2JsonDecoder jackson2JsonDecoder = new Jackson2JsonDecoder(new ObjectMapper(),
                MediaType.APPLICATION_JSON);

        return jackson2JsonDecoder;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
