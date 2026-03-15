package com.tuapp.quiz_tutor.service;

import com.tuapp.quiz_tutor.model.AuthResponse;
import com.tuapp.quiz_tutor.model.LoginRequest;
import com.tuapp.quiz_tutor.model.RegisterRequest;
import com.tuapp.quiz_tutor.model.Usuario;
import com.tuapp.quiz_tutor.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.Normalizer;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {
        if (usuarioRepository.existsByCorreo(request.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado");
        }
        String username = generateUsername(request.getNombre());
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setCorreo(request.getCorreo());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuarioRepository.save(usuario);
        String token = jwtService.generateToken(username);
        return new AuthResponse(token, username, "Registro exitoso. Tu usuario es: " + username);
    }

    public AuthResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }
        String token = jwtService.generateToken(usuario.getUsername());
        return new AuthResponse(token, usuario.getUsername(), "Bienvenido " + usuario.getUsername());
    }

    private String generateUsername(String nombre) {
        String normalized = Normalizer.normalize(nombre.toLowerCase().trim(), Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .replaceAll("\\s+", ".")
                .replaceAll("[^a-z0-9.]", "");
        String base = normalized;
        String username = base;
        int counter = 1;
        while (usuarioRepository.existsByUsername(username)) {
            username = base + counter;
            counter++;
        }
        return username;
    }
}