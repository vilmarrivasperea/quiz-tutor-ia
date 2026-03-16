package com.tuapp.quiz_tutor.model;

import java.util.List;

public class LearnRequest {
    private String tema;
    private String pregunta;
    private String respuestaCorrecta;
    private List<String> opciones;

    public String getTema() { return tema; }
    public String getPregunta() { return pregunta; }
    public String getRespuestaCorrecta() { return respuestaCorrecta; }
    public List<String> getOpciones() { return opciones; }
    public void setTema(String tema) { this.tema = tema; }
    public void setPregunta(String pregunta) { this.pregunta = pregunta; }
    public void setRespuestaCorrecta(String respuestaCorrecta) { this.respuestaCorrecta = respuestaCorrecta; }
    public void setOpciones(List<String> opciones) { this.opciones = opciones; }
}