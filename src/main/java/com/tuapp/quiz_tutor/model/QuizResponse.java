package com.tuapp.quiz_tutor.model;

import java.util.List;

public class QuizResponse {
    private String pregunta;
    private List<String> opciones;
    private int correcta;
    private String tema;

    public String getPregunta() { return pregunta; }
    public List<String> getOpciones() { return opciones; }
    public int getCorrecta() { return correcta; }
    public String getTema() { return tema; }
    public void setPregunta(String pregunta) { this.pregunta = pregunta; }
    public void setOpciones(List<String> opciones) { this.opciones = opciones; }
    public void setCorrecta(int correcta) { this.correcta = correcta; }
    public void setTema(String tema) { this.tema = tema; }
}