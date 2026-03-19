package com.tuapp.quiz_tutor.service;

import com.tuapp.quiz_tutor.model.Logro;
import com.tuapp.quiz_tutor.model.Usuario;
import com.tuapp.quiz_tutor.repository.LogroRepository;
import com.tuapp.quiz_tutor.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LogroService {

    @Autowired
    private LogroRepository logroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Logro> getLogros(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow();
        return logroRepository.findByUsuario(usuario);
    }

    public void verificarLogros(String username, int totalQuizzes, int puntaje, int correctas) {
        Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow();

        otorgar(usuario, "PRIMER_QUIZ", "Primer paso", "Completaste tu primer quiz", "🎯",
            totalQuizzes >= 1);

        otorgar(usuario, "QUIZ_PERFECTO", "¡Perfecto!", "Obtuviste 100% en un quiz", "💯",
            puntaje == 100);

        otorgar(usuario, "5_QUIZZES", "Estudiante dedicado", "Completaste 5 quizzes", "📚",
            totalQuizzes >= 5);

        otorgar(usuario, "10_QUIZZES", "Experto en quizzes", "Completaste 10 quizzes", "🏆",
            totalQuizzes >= 10);

        otorgar(usuario, "RACHA_CORRECTAS", "En llamas", "5 respuestas correctas seguidas", "🔥",
            correctas >= 5);

        otorgar(usuario, "MAESTRO", "Maestro del conocimiento", "Completaste 25 quizzes", "🧠",
            totalQuizzes >= 25);
    }

    private void otorgar(Usuario usuario, String codigo, String nombre, String desc, String emoji, boolean condicion) {
        if (condicion && !logroRepository.existsByUsuarioAndCodigo(usuario, codigo)) {
            logroRepository.save(new Logro(usuario, codigo, nombre, desc, emoji));
        }
    }
}