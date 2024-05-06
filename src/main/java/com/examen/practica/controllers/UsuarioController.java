package com.examen.practica.controllers;

import com.examen.practica.models.dto.UsuarioDto;
import com.examen.practica.models.entities.Usuario;
import com.examen.practica.services.UsuarioService;
import jakarta.validation.Valid;
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
@RequestMapping("/api/v1/usuario")
@CrossOrigin(originPatterns = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<UsuarioDto> list(){
        return usuarioService.obtenerUsuarios();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){
        Optional<UsuarioDto> userDtoOptional = usuarioService.encontrarUsuario(id);

        if(userDtoOptional.isPresent()){
            return ResponseEntity.ok(userDtoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Usuario user, BindingResult result) {
        if (result.hasErrors()) {
            return validation(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardarUsuario(user));

    }

    private ResponseEntity<?> validation(BindingResult result){
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err-> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
