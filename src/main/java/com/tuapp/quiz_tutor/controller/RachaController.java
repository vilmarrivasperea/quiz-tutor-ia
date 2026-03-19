/*package com.tuapp.quiz_tutor.controller;

import com.tuapp.quiz_tutor.service.JwtService;
import com.tuapp.quiz_tutor.service.RachaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/racha")
public class RachaController {

    @Autowired
    private RachaService rachaService;

    @Autowired
    private JwtService jwtService;

    @GetMapping
    public ResponseEntity<?> getRacha(@RequestHeader("Authorization") String authHeader) {
        try {
            String username = jwtService.getUsernameFromToken(authHeader.replace("Bearer ", ""));
            return ResponseEntity.ok(rachaService.getRacha(username));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/actualizar")
    public ResponseEntity<?> actualizar(@RequestHeader("Authorization") String authHeader) {
        try {
            String username = jwtService.getUsernameFromToken(authHeader.replace("Bearer ", ""));
            return ResponseEntity.ok(rachaService.actualizarRacha(username));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}*/