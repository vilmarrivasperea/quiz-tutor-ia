package com.tuapp.quiz_tutor.service;

import com.tuapp.quiz_tutor.model.QuizHistorial;
import com.tuapp.quiz_tutor.repository.QuizHistorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private QuizHistorialRepository historialRepository;

    @Autowired
    private LogroService logroService;

    public void guardarResultado(String username, String tema, int totalPreguntas, int correctas) {
        QuizHistorial historial = new QuizHistorial();
        historial.setUsername(username);
        historial.setTema(tema);
        historial.setTotalPreguntas(totalPreguntas);
        historial.setCorrectas(correctas);
        historialRepository.save(historial);

        // Verificar logros después de guardar
        Long totalQuizzes = historialRepository.countByUsername(username);
        int puntaje = totalPreguntas > 0 ? (int) Math.round((correctas * 100.0) / totalPreguntas) : 0;
        logroService.verificarLogros(username, totalQuizzes.intValue(), puntaje, correctas);
    }

    public Map<String, Object> getDashboard(String username) {
        List<QuizHistorial> historial = historialRepository.findByUsernameOrderByFechaDesc(username);
        Double promedio = historialRepository.findPromedioPuntajeByUsername(username);
        Long totalQuizzes = historialRepository.countByUsername(username);
        Long totalCorrectas = historialRepository.sumCorrectasByUsername(username);
        Long totalPreguntas = historialRepository.sumTotalPreguntasByUsername(username);

        Map<String, Object> dashboard = new HashMap<>();
        dashboard.put("historial", historial);
        dashboard.put("promedio", promedio != null ? Math.round(promedio) : 0);
        dashboard.put("totalQuizzes", totalQuizzes != null ? totalQuizzes : 0);
        dashboard.put("totalCorrectas", totalCorrectas != null ? totalCorrectas : 0);
        dashboard.put("totalPreguntas", totalPreguntas != null ? totalPreguntas : 0);
        return dashboard;
    }
}