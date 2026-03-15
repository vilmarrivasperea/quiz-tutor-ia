package com.tuapp.quiz_tutor.model;

import lombok.Data;

import java.util.List;

@Data
public class QuizResponse {

    private String pregunta;
    private List<String> opciones;
    private int correcta;
    private String tema;
}
