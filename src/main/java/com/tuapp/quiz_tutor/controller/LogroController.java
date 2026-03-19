package com.tuapp.quiz_tutor.controller;

import com.tuapp.quiz_tutor.model.Logro;
import com.tuapp.quiz_tutor.service.JwtService;
import com.tuapp.quiz_tutor.service.LogroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/logros")
public class LogroController {

    @Autowired
    private LogroService logroService;

    @Autowired
    private JwtService jwtService;

    @GetMapping
    public ResponseEntity<?> getLogros(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            String username = jwtService.getUsernameFromToken(token);
            List<Logro> logros = logroService.getLogros(username);
            List<Map<String, String>> resultado = logros.stream().map(l -> Map.of(
                "codigo", l.getCodigo(),
                "nombre", l.getNombre(),
                "descripcion", l.getDescripcion(),
                "emoji", l.getEmoji(),
                "fecha", l.getFechaObtenido().toString()
            )).collect(Collectors.toList());
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
