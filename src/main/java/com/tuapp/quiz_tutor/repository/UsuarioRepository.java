package com.tuapp.quiz_tutor.repository;

import com.tuapp.quiz_tutor.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
    Optional<Usuario> findByCorreo(String correo);
    boolean existsByUsername(String username);
    boolean existsByCorreo(String correo);
}