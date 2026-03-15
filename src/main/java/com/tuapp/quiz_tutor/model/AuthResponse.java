package com.tuapp.quiz_tutor.model;

public class AuthResponse {
    private String token;
    private String username;
    private String mensaje;

    public AuthResponse(String token, String username, String mensaje) {
        this.token = token;
        this.username = username;
        this.mensaje = mensaje;
    }

    public String getToken() { return token; }
    public String getUsername() { return username; }
    public String getMensaje() { return mensaje; }
    public void setToken(String token) { this.token = token; }
    public void setUsername(String username) { this.username = username; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}