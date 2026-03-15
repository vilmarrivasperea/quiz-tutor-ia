package com.tuapp.quiz_tutor.model;

import lombok.Data;

import java.util.List;

@Data
public class LearnRequest {

    private String tema;
    private String pregunta;
    private String respuestaCorrecta;
    private List<String> opciones; // tipos de ayuda solicitados (ej: "explicar", "resumen", "ejercicio")
}
