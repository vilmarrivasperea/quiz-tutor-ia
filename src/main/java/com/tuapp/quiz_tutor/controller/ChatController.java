package com.tuapp.quiz_tutor.controller;

import com.tuapp.quiz_tutor.service.GroqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private GroqService groqService;

    @PostMapping("/mensaje")
    public ResponseEntity<?> chat(@RequestBody Map<String, Object> body) {
        try {
            String mensaje = (String) body.get("mensaje");
            List<Map<String, String>> historial = (List<Map<String, String>>) body.get("historial");
            String respuesta = groqService.chat(mensaje, historial);
            return ResponseEntity.ok(Map.of("respuesta", respuesta));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}