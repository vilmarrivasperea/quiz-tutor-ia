package com.tuapp.quiz_tutor.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuapp.quiz_tutor.model.QuizResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class GroqService {

    private static final String GROQ_URL = "https://api.groq.com/openai/v1/chat/completions";
    private static final String MODEL = "llama-3.3-70b-versatile";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${groq.api.key}")
    private String apiKey;

    public GroqService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public List<QuizResponse> generateQuiz(String texto, int numPreguntas) {
        String prompt = String.format("""
            Genera exactamente %d preguntas de opción múltiple basadas en el siguiente texto.
            Responde ÚNICAMENTE con un JSON válido, un array de objetos, cada uno con estas claves exactas:
            - "pregunta": string
            - "opciones": array de 4 strings
            - "correcta": número (índice 0-3 de la opción correcta)
            - "tema": string (tema o categoría de la pregunta)
            No incluyas explicaciones ni texto fuera del JSON.

            Texto:
            %s
            """, numPreguntas, texto);

        String json = callGroq(prompt);
        return parseQuizJson(json);
    }

    public String explainTopic(String tema, String pregunta, String respuestaCorrecta) {
        String prompt = String.format("""
            Como tutor, explica de forma clara y breve el tema "%s" en el contexto de esta pregunta:
            "%s"
            La respuesta correcta es: %s
            Da una explicación didáctica (2-4 párrafos).
            """, tema, pregunta, respuestaCorrecta);
        return callGroq(prompt);
    }

    public String generateSummary(String tema) {
        String prompt = String.format(
            "Genera un resumen didáctico y claro del tema: %s. Usa 3-5 párrafos.",
            tema);
        return callGroq(prompt);
    }

    public String generateExercise(String tema, String pregunta) {
        String prompt = String.format("""
            Para el tema "%s" y la pregunta "%s", genera un ejercicio o ejemplo práctico breve
            que ayude a afianzar el concepto (1-3 párrafos).
            """, tema, pregunta);
        return callGroq(prompt);
    }

    private String callGroq(String userContent) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);
        headers.set("Accept-Charset", "UTF-8");

        Map<String, Object> body = new HashMap<>();
        body.put("model", MODEL);
        body.put("messages", List.of(
            Map.of("role", "user", "content", userContent)
        ));
        body.put("temperature", 0.3);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<GroqChatResponse> response = restTemplate.exchange(
            GROQ_URL,
            HttpMethod.POST,
            request,
            GroqChatResponse.class
        );

        if (response.getBody() == null || response.getBody().getChoices() == null
                || response.getBody().getChoices().isEmpty()) {
            throw new RuntimeException("Respuesta vacía de Groq");
        }
        return response.getBody().getChoices().get(0).getMessage().getContent();
    }

    private List<QuizResponse> parseQuizJson(String json) {
        try {
            String content = json.trim();
            if (content.startsWith("```")) {
                int start = content.indexOf('[');
                int end = content.lastIndexOf(']') + 1;
                if (start >= 0 && end > start) {
                    content = content.substring(start, end);
                }
            }
            return objectMapper.readValue(content, new TypeReference<List<QuizResponse>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Error al parsear JSON de preguntas: " + e.getMessage());
        }
    }

    @SuppressWarnings("unused")
    private static class GroqChatResponse {
        private List<Choice> choices;

        public List<Choice> getChoices() { return choices; }
        public void setChoices(List<Choice> choices) { this.choices = choices; }
    }

    @SuppressWarnings("unused")
    private static class Choice {
        private Message message;
        public Message getMessage() { return message; }
        public void setMessage(Message message) { this.message = message; }
    }

    @SuppressWarnings("unused")
    private static class Message {
        private String content;
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
    }
}
