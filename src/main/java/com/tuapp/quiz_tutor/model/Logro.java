package com.tuapp.quiz_tutor.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "logros")
public class Logro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private String codigo;
    private String nombre;
    private String descripcion;
    private String emoji;
    private LocalDateTime fechaObtenido;

    public Logro() {}

    public Logro(Usuario usuario, String codigo, String nombre, String descripcion, String emoji) {
        this.usuario = usuario;
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.emoji = emoji;
        this.fechaObtenido = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Usuario getUsuario() { return usuario; }
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public String getEmoji() { return emoji; }
    public LocalDateTime getFechaObtenido() { return fechaObtenido; }
}