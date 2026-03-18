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

    private static final String GEMINI_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent";

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
            No incluyas explicaciones ni texto fuera del JSON. No uses markdown ni backticks.

            Texto:
            %s
            """, numPreguntas, texto);

        String json = callGemini(prompt);
        return parseQuizJson(json);
    }

    public String explainTopic(String tema, String pregunta, String respuestaCorrecta) {
        String prompt = String.format("""
            Como tutor, explica de forma clara y breve el tema "%s" en el contexto de esta pregunta:
            "%s"
            La respuesta correcta es: %s
            Da una explicación didáctica (2-4 párrafos).
            """, tema, pregunta, respuestaCorrecta);
        return callGemini(prompt);
    }

    public String generateSummary(String tema) {
        String prompt = String.format(
            "Genera un resumen didáctico y claro del tema: %s. Usa 3-5 párrafos.",
            tema);
        return callGemini(prompt);
    }

    public String generateExercise(String tema, String pregunta) {
        String prompt = String.format("""
            Para el tema "%s" y la pregunta "%s", genera un ejercicio o ejemplo práctico breve
            que ayude a afianzar el concepto (1-3 párrafos).
            """, tema, pregunta);
        return callGemini(prompt);
    }

    private String callGemini(String userContent) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> part = new HashMap<>();
        part.put("text", userContent);

        Map<String, Object> content = new HashMap<>();
        content.put("parts", List.of(part));

        Map<String, Object> body = new HashMap<>();
        body.put("contents", List.of(content));

        String urlWithKey = GEMINI_URL + "?key=" + apiKey;

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<GeminiResponse> response = restTemplate.exchange(
            urlWithKey,
            HttpMethod.POST,
            request,
            GeminiResponse.class
        );

        if (response.getBody() == null || response.getBody().getCandidates() == null
                || response.getBody().getCandidates().isEmpty()) {
            throw new RuntimeException("Respuesta vacía de Gemini");
        }

        return response.getBody().getCandidates().get(0)
                .getContent().getParts().get(0).getText();
    }

    private List<QuizResponse> parseQuizJson(String json) {
        try {
            String content = json.trim();
            if (content.contains("```")) {
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

    private static class GeminiResponse {
        private List<Candidate> candidates;
        public List<Candidate> getCandidates() { return candidates; }
        public void setCandidates(List<Candidate> candidates) { this.candidates = candidates; }
    }

    private static class Candidate {
        private ContentBlock content;
        public ContentBlock getContent() { return content; }
        public void setContent(ContentBlock content) { this.content = content; }
    }

    private static class ContentBlock {
        private List<Part> parts;
        public List<Part> getParts() { return parts; }
        public void setParts(List<Part> parts) { this.parts = parts; }
    }

    private static class Part {
        private String text;
        public String getText() { return text; }
        public void setText(String text) { this.text = text; }
    }
}