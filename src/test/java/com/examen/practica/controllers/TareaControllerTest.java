package com.examen.practica.controllers;

import com.examen.practica.models.entities.Tarea;
import com.examen.practica.services.TareaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TareaController.class)
//@WithMockUser(username = "username", password = "password", roles = "USER")
class TareaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TareaService tareaService;

    private Tarea tarea;


    @BeforeEach
    void setUp() {
        Tarea tarea = new Tarea(1L, "Tarea 1", "primera tarea", false);
    }



    @Test
    @DisplayName("Obtener la tarea por el Id de tarea, datos incorrectos")
    void obtenerTareaPorIdNotExist() throws Exception  {
        Mockito.when(tareaService.obtenerTarea(1L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/v1/tarea/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }


    @Test
    @DisplayName("Obtener la tarea por el Id de tarea, datos correctos")
    void obtenerTareaPorId() throws Exception {
        Optional<Tarea> tareaOptional = Optional.of(new Tarea(1L, "Tarea 1", "primera tarea", false));
        Mockito.when(tareaService.obtenerTarea(1L)).thenReturn(tareaOptional);
        mockMvc.perform(get("/api/v1/tarea/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value(tareaOptional.get().getTitulo()));

    }

    @Test
    void crearTarea() throws Exception {
        Tarea tareaTest = new Tarea(1L, "Tarea 1", "primera tarea", false);
        Mockito.when(tareaService.guardarTarea(tareaTest)).thenReturn(tarea);
        mockMvc.perform(post("/api/v1/tarea/save").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"titulo\":\"Tarea 1\",\n" +
                        "    \"descripcion\": \"primera tarea\",\n" +
                        "    \"estado\":false \n" +
                        "}"))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Eliminar la tarea por el ID de tarea, Fallido")
    void eliminarTarea() throws Exception {
        Mockito.doNothing().when(tareaService).eliminarTarea(2L);
        mockMvc.perform(delete("/api/v1/tarea/1")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNoContent());
    }


}