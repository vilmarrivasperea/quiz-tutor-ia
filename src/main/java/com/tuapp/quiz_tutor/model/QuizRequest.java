package com.tuapp.quiz_tutor.model;

public class QuizRequest {
    private String texto;
    private int numPreguntas;

    public String getTexto() { return texto; }
    public int getNumPreguntas() { return numPreguntas; }
    public void setTexto(String texto) { this.texto = texto; }
    public void setNumPreguntas(int numPreguntas) { this.numPreguntas = numPreguntas; }
}