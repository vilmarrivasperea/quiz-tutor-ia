package com.tuapp.quiz_tutor.model;

public class QuizRequest {
    private String texto;
    private int numPreguntas;
    private String dificultad;

    public String getTexto() { return texto; }
    public int getNumPreguntas() { return numPreguntas; }
    public String getDificultad() { return dificultad; }
    public void setTexto(String texto) { this.texto = texto; }
    public void setNumPreguntas(int numPreguntas) { this.numPreguntas = numPreguntas; }
    public void setDificultad(String dificultad) { this.dificultad = dificultad; }
}