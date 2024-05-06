package com.examen.practica.controllers;

import com.examen.practica.models.entities.Tarea;
import com.examen.practica.services.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/tarea")
public class TareaController {

    @Autowired
    private TareaService tareaService;

    @GetMapping
    public List<Tarea> traerTodosLasTareas(){
        return tareaService.listaDeTareas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerTareaPorId(@PathVariable("id") Long id){
        Optional<Tarea> tareaOptional = tareaService.obtenerTarea(id);
        if (tareaOptional.isPresent()) {
            return ResponseEntity.ok(tareaOptional);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> crearTarea (@RequestBody Tarea tarea, BindingResult result){
        if(result.hasErrors()){
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(tareaService.guardarTarea(tarea));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarTarea(@RequestBody Tarea tarea, BindingResult result,@PathVariable("id") Long id ){
        if (result.hasErrors()){
            return validation(result);
        }

        Optional<Tarea> tareaOptional = tareaService.obtenerTarea(id);
        if (tareaOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(tareaService.actualizarTarea(tarea,id));
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> eliminarTarea(@PathVariable("id") Long id){
        Optional<Tarea> tarea = tareaService.obtenerTarea(id);
        if (tarea.isPresent()){
            tareaService.eliminarTarea(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private ResponseEntity<?> validation(BindingResult result){
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err->{
            errors.put(err.getField(),"El campo " + err.getField() + " " + err.getDefaultMessage() );
        });
        return ResponseEntity.badRequest().body(errors);
    }


}
