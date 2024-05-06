package com.examen.practica.services;

import com.examen.practica.models.dto.UsuarioDto;
import com.examen.practica.models.entities.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<UsuarioDto> obtenerUsuarios() ;
    Optional<UsuarioDto> encontrarUsuario(Long id);
    UsuarioDto guardarUsuario(Usuario usuario);

}
