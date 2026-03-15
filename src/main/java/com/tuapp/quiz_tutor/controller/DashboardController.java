package com.tuapp.quiz_tutor.controller;

import com.tuapp.quiz_tutor.service.DashboardService;
import com.tuapp.quiz_tutor.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private JwtService jwtService;

    @GetMapping
    public ResponseEntity<?> getDashboard(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            String username = jwtService.getUsernameFromToken(token);
            Map<String, Object> dashboard = dashboardService.getDashboard(username);
            return ResponseEntity.ok(dashboard);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Token inválido");
        }
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> guardarResultado(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Map<String, Object> body) {
        try {
            String token = authHeader.replace("Bearer ", "");
            String username = jwtService.getUsernameFromToken(token);
            String tema = (String) body.get("tema");
            int totalPreguntas = (int) body.get("totalPreguntas");
            int correctas = (int) body.get("correctas");
            dashboardService.guardarResultado(username, tema, totalPreguntas, correctas);
            return ResponseEntity.ok("Resultado guardado");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al guardar");
        }
    }
}