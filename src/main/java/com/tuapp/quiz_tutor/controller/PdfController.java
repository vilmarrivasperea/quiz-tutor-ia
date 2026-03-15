package com.tuapp.quiz_tutor.controller;

import com.tuapp.quiz_tutor.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

@RestController
@RequestMapping("/api/pdf")
@CrossOrigin(origins = "*")
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @PostMapping("/extraer")
    public ResponseEntity<?> extraerTexto(@RequestParam("archivo") MultipartFile archivo) {
        try {
            if (archivo.isEmpty()) {
                return ResponseEntity.badRequest().body("No se recibió ningún archivo");
            }
            String contentType = archivo.getContentType();
            if (contentType == null || !contentType.equals("application/pdf")) {
                return ResponseEntity.badRequest().body("Solo se aceptan archivos PDF");
            }
            String texto = pdfService.extractText(archivo);
            return ResponseEntity.ok(Map.of("texto", texto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al procesar el PDF: " + e.getMessage());
        }
    }
}