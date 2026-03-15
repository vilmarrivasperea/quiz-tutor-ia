package com.tuapp.quiz_tutor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Configuration
public class WebConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        // Forzar UTF-8 como charset por defecto para convertidores de String
        restTemplate.setMessageConverters(
                restTemplate.getMessageConverters().stream().peek(converter -> {
                    if (converter instanceof org.springframework.http.converter.StringHttpMessageConverter stringConverter) {
                        stringConverter.setDefaultCharset(StandardCharsets.UTF_8);
                    }
                }).collect(Collectors.toList())
        );
        return restTemplate;
    }
}
