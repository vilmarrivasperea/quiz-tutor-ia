package com.tuapp.quiz_tutor.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "quiz_historial")
public class QuizHistorial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String tema;

    private int totalPreguntas;
    private int correctas;
    private int puntaje;
    private LocalDateTime fecha;

    @PrePersist
    public void prePersist() {
        this.fecha = LocalDateTime.now();
        this.puntaje = totalPreguntas > 0 ? (correctas * 100 / totalPreguntas) : 0;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getTema() { return tema; }
    public int getTotalPreguntas() { return totalPreguntas; }
    public int getCorrectas() { return correctas; }
    public int getPuntaje() { return puntaje; }
    public LocalDateTime getFecha() { return fecha; }
    public void setId(Long id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setTema(String tema) { this.tema = tema; }
    public void setTotalPreguntas(int totalPreguntas) { this.totalPreguntas = totalPreguntas; }
    public void setCorrectas(int correctas) { this.correctas = correctas; }
    public void setPuntaje(int puntaje) { this.puntaje = puntaje; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}