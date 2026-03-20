package com.tuapp.quiz_tutor.controller;

import com.tuapp.quiz_tutor.model.LearnRequest;
import com.tuapp.quiz_tutor.model.QuizRequest;
import com.tuapp.quiz_tutor.model.QuizResponse;
import com.tuapp.quiz_tutor.service.GroqService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    private final GroqService groqService;

    public QuizController(GroqService groqService) {
        this.groqService = groqService;
    }

    @PostMapping("/generate")
    public ResponseEntity<List<QuizResponse>> generate(@RequestBody QuizRequest request) {
        String dificultad = request.getDificultad() != null ? request.getDificultad() : "medio";
        List<QuizResponse> quiz = groqService.generateQuiz(request.getTexto(), request.getNumPreguntas(), dificultad);
        return ResponseEntity.ok(quiz);
    }

    @PostMapping("/learn")
    public ResponseEntity<Map<String, String>> learn(@RequestBody LearnRequest request) {
        Map<String, String> result = new LinkedHashMap<>();
        String tema = request.getTema();
        String pregunta = request.getPregunta();
        String respuestaCorrecta = request.getRespuestaCorrecta();
        List<String> opciones = request.getOpciones() != null ? request.getOpciones() : List.of();

        for (String tipo : opciones) {
            String valor = tipo != null ? tipo.toLowerCase().trim() : "";
            switch (valor) {
                case "explicar", "explicación" -> result.put("explicacion", groqService.explainTopic(tema, pregunta, respuestaCorrecta));
                case "resumen" -> result.put("resumen", groqService.generateSummary(tema));
                case "ejercicio" -> result.put("ejercicio", groqService.generateExercise(tema, pregunta));
                default -> result.put(tipo, groqService.explainTopic(tema, pregunta, respuestaCorrecta));
            }
        }
        return ResponseEntity.ok(result);
    }
}