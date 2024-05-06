package com.examen.practica.services.Impl;

import com.examen.practica.models.entities.Tarea;
import com.examen.practica.repostories.TareaRepository;
import com.examen.practica.services.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TareaServiceImpl implements TareaService {

     @Autowired
     private TareaRepository tareaRepository;


    @Override
    public List<Tarea> listaDeTareas() {
        return (List<Tarea>) tareaRepository.findAll();
    }

    @Override
    public Optional<Tarea> obtenerTarea(Long id) {
        return tareaRepository.findById(id);
    }

    @Override
    public Tarea guardarTarea(Tarea tarea) {
        return tareaRepository.save(tarea);
    }

    @Override
    public Optional<Tarea> actualizarTarea(Tarea tarea, Long id) {
        Optional<Tarea> tareaOptional = tareaRepository.findById(id);
        Tarea tarea1 = null;
        if (tareaOptional.isPresent()){
            Tarea tareaDB = new Tarea();
            tareaDB = tareaOptional.orElseThrow();
            tareaDB.setTitulo(tarea.getTitulo());
            tareaDB.setDescripcion(tarea.getDescripcion());
            tareaDB.setEstado(tarea.getEstado());
            tarea1 = tareaRepository.save(tareaDB);
        }
        return Optional.ofNullable(tarea1);
    }

    @Override
    public void eliminarTarea(Long id) {
        tareaRepository.deleteById(id);
    }
}
