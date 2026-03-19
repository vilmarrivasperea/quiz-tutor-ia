package com.tuapp.quiz_tutor.controller;

import com.tuapp.quiz_tutor.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @PostMapping("/extraer")
    public ResponseEntity<?> extraerTexto(@RequestParam("archivo") MultipartFile archivo) {
        System.out.println("=== PDF RECIBIDO ===");
        System.out.println("Nombre: " + archivo.getOriginalFilename());
        System.out.println("Tamaño: " + archivo.getSize());
        System.out.println("Tipo: " + archivo.getContentType());
        try {
            if (archivo.isEmpty()) {
                System.out.println("ERROR: archivo vacío");
                return ResponseEntity.badRequest().body("No se recibió ningún archivo");
            }
            String contentType = archivo.getContentType();
            if (contentType == null || !contentType.equals("application/pdf")) {
                System.out.println("ERROR: tipo incorrecto: " + contentType);
                return ResponseEntity.badRequest().body("Solo se aceptan archivos PDF");
            }
            System.out.println("Extrayendo texto...");
            String texto = pdfService.extractText(archivo);
            System.out.println("Texto extraído: " + texto.length() + " caracteres");
            return ResponseEntity.ok(Map.of("texto", texto));
        } catch (Exception e) {
            System.out.println("EXCEPCION: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error al procesar el PDF: " + e.getMessage());
        }
    }
}