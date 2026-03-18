package com.tuapp.quiz_tutor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        // Forzar UTF-8 como charset por defecto
        restTemplate.setMessageConverters(
                restTemplate.getMessageConverters().stream().peek(converter -> {
                    if (converter instanceof org.springframework.http.converter.StringHttpMessageConverter stringConverter) {
                        stringConverter.setDefaultCharset(StandardCharsets.UTF_8);
                    }
                }).collect(Collectors.toList())
        );
        return restTemplate;
    }

    // Configuración de CORS para permitir peticiones desde el navegador en Railway
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://quiz-tutor-ia-production.up.railway.app", "http://localhost:8080")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}