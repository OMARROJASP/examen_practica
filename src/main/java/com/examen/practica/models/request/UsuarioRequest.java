package com.examen.practica.models.request;

import jakarta.validation.constraints.NotBlank;

public class UsuarioRequest {
    @NotBlank
    private String nombre;
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
