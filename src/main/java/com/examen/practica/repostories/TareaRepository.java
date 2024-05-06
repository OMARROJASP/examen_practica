package com.examen.practica.repostories;

import com.examen.practica.models.entities.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface TareaRepository extends JpaRepository<Tarea, Long> {
}
