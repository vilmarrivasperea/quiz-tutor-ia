package com.tuapp.quiz_tutor.model;

public class RegisterRequest {
    private String nombre;
    private String correo;
    private String password;

    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    public String getPassword() { return password; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCorreo(String correo) { this.correo = correo; }
    public void setPassword(String password) { this.password = password; }
}