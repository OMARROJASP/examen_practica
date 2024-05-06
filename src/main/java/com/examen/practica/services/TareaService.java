package com.examen.practica.services;

import com.examen.practica.models.entities.Tarea;

import java.util.List;
import java.util.Optional;

public interface TareaService {

    List<Tarea> listaDeTareas();

    Optional<Tarea> obtenerTarea(Long id);
    Tarea guardarTarea(Tarea tarea);

    Optional<Tarea> actualizarTarea(Tarea tarea, Long id);
    void eliminarTarea(Long id);

}
