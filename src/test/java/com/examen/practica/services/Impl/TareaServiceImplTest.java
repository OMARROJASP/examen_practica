package com.examen.practica.services.Impl;

import com.examen.practica.models.entities.Tarea;
import com.examen.practica.repostories.TareaRepository;

import org.junit.jupiter.api.*;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TareaServiceImplTest {

    @Autowired
    private TareaServiceImpl tareaService;

    @MockBean
    private TareaRepository tareaRepository;


    @BeforeEach
    void setUp(){
        Tarea tarea = new Tarea(1L, "Tarea 1", "primera tarea", false);
        Mockito.when(tareaRepository.findById(1L)).thenReturn(Optional.of(tarea));
        Mockito.doNothing().when(tareaRepository).deleteById(1L);

    }

    @Test
    @DisplayName("Prueba guardar la tarea enviado datos correctos")
    void guardarTarea() {
        Tarea tarea = new Tarea(1L, "Tarea 1", "primera tarea", false);
        Mockito.when(tareaRepository.save(tarea)).thenReturn(tarea);
        Tarea resultado = tareaService.guardarTarea(tarea);
        assertEquals(tarea, resultado);
        assertEquals(tarea.getDescripcion(), resultado.getDescripcion());
    }

    @Test
    @DisplayName("Prueba lista la tarea almacenadas datos correctos")
    void listaDeTareas() {
        List<Tarea> tareas = Arrays.asList(
                new Tarea(1L, "Tarea 1", "primera tarea", false),
                new Tarea(2L, "Tarea 2", "segunda tarea", false),
                new Tarea(3L, "Tarea 3", "tercera tarea", false)
        );

        when(tareaRepository.findAll()).thenReturn(tareas);
        List<Tarea> resultado = tareaService.listaDeTareas();

        assertEquals(tareas.size(), resultado.size());

        // verficar
        for(int i =0; i< tareas.size(); i++){
            assertEquals(tareas.get(i), resultado.get(i));
        }
    }

    @Test
    @DisplayName("Prueba obtener la tarea enviado datos correctos")
    void obtenerTarea() {
        Long tareaId = 1L;
        Tarea tarea1  = tareaService.obtenerTarea(tareaId).get();
        assertEquals(tareaId, tarea1.getId());
        System.out.println("tareaId = " + tarea1);
    }



    @Test
    @DisplayName("Prueba eliminar la tarea enviado datos correctos")
    void eliminarTarea() {
        tareaService.eliminarTarea(1L);
        Mockito.verify(tareaRepository,Mockito.times(1)).deleteById(1L);
    }
}