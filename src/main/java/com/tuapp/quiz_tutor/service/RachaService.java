/*package com.tuapp.quiz_tutor.service;

import com.tuapp.quiz_tutor.model.Usuario;
import com.tuapp.quiz_tutor.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class RachaService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Map<String, Object> getRacha(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow();
        return buildRachaInfo(usuario);
    }

    public Map<String, Object> actualizarRacha(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow();
        LocalDate hoy = LocalDate.now();
        LocalDate ultima = usuario.getUltimaActividad();

        boolean subioNivel = false;
        int nivelAnterior = getNivel(usuario.getRachaDias());

        if (ultima == null || ultima.isBefore(hoy.minusDays(1))) {
            // Racha rota o primera vez
            usuario.setRachaDias(1);
        } else if (ultima.isBefore(hoy)) {
            // Día siguiente — aumenta racha
            usuario.setRachaDias(usuario.getRachaDias() + 1);
        }
        // Si ultima == hoy, no hace nada (ya estudió hoy)

        usuario.setUltimaActividad(hoy);
        usuarioRepository.save(usuario);

        int nivelNuevo = getNivel(usuario.getRachaDias());
        subioNivel = nivelNuevo > nivelAnterior;

        Map<String, Object> info = buildRachaInfo(usuario);
        info.put("subioNivel", subioNivel);
        return info;
    }

    private Map<String, Object> buildRachaInfo(Usuario usuario) {
        int dias = usuario.getRachaDias();
        Map<String, Object> info = new HashMap<>();
        info.put("dias", dias);
        info.put("nivel", getNivel(dias));
        info.put("avatar", getAvatar(dias));
        info.put("nombreAvatar", getNombreAvatar(dias));
        info.put("mensaje", getMensaje(dias));
        info.put("proximoEn", getProximoEn(dias));
        info.put("proximoAvatar", getProximoAvatar(dias));
        return info;
    }

    private int getNivel(int dias) {
        if (dias >= 21) return 6;
        if (dias >= 14) return 5;
        if (dias >= 7)  return 4;
        if (dias >= 5)  return 3;
        if (dias >= 3)  return 2;
        return 1;
    }

    private String getAvatar(int dias) {
        if (dias >= 21) return "🐉";
        if (dias >= 14) return "🦁";
        if (dias >= 7)  return "🐺";
        if (dias >= 5)  return "🦊";
        if (dias >= 3)  return "🐱";
        return "🐣";
    }

    private String getNombreAvatar(int dias) {
        if (dias >= 21) return "Dragón Legendario";
        if (dias >= 14) return "León Feroz";
        if (dias >= 7)  return "Lobo Alfa";
        if (dias >= 5)  return "Zorro Astuto";
        if (dias >= 3)  return "Gatito Curioso";
        return "Pollito Aprendiz";
    }

    private String getMensaje(int dias) {
        if (dias >= 21) return "¡Eres una leyenda! 🔥";
        if (dias >= 14) return "¡Imparable! Dos semanas seguidas";
        if (dias >= 7)  return "¡Una semana completa! Increíble";
        if (dias >= 5)  return "¡Vas muy fuerte! Sigue así";
        if (dias >= 3)  return "¡Buen ritmo! Ya vas tomando hábito";
        if (dias == 1)  return "¡Bienvenido! Hoy empezó tu racha";
        return "¡Vuelves! Reconstruye tu racha";
    }

    private int getProximoEn(int dias) {
        if (dias >= 21) return 0;
        if (dias >= 14) return 21 - dias;
        if (dias >= 7)  return 14 - dias;
        if (dias >= 5)  return 7 - dias;
        if (dias >= 3)  return 5 - dias;
        return 3 - dias;
    }

    private String getProximoAvatar(int dias) {
        if (dias >= 21) return "👑";
        if (dias >= 14) return "🐉";
        if (dias >= 7)  return "🦁";
        if (dias >= 5)  return "🐺";
        if (dias >= 3)  return "🦊";
        return "🐱";
    }
}*/