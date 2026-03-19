package com.tuapp.quiz_tutor.repository;

import com.tuapp.quiz_tutor.model.Logro;
import com.tuapp.quiz_tutor.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LogroRepository extends JpaRepository<Logro, Long> {
    List<Logro> findByUsuario(Usuario usuario);
    boolean existsByUsuarioAndCodigo(Usuario usuario, String codigo);
}