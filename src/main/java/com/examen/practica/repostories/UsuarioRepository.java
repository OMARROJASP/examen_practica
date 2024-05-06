package com.examen.practica.repostories;

import com.examen.practica.models.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    Optional<Usuario> findByNombre(String nombre);
}
