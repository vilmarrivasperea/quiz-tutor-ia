package com.tuapp.quiz_tutor.repository;

import com.tuapp.quiz_tutor.model.QuizHistorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuizHistorialRepository extends JpaRepository<QuizHistorial, Long> {

    List<QuizHistorial> findByUsernameOrderByFechaDesc(String username);

    @Query("SELECT AVG(q.puntaje) FROM QuizHistorial q WHERE q.username = :username")
    Double findPromedioPuntajeByUsername(String username);

    @Query("SELECT COUNT(q) FROM QuizHistorial q WHERE q.username = :username")
    Long countByUsername(String username);

    @Query("SELECT SUM(q.correctas) FROM QuizHistorial q WHERE q.username = :username")
    Long sumCorrectasByUsername(String username);

    @Query("SELECT SUM(q.totalPreguntas) FROM QuizHistorial q WHERE q.username = :username")
    Long sumTotalPreguntasByUsername(String username);
}