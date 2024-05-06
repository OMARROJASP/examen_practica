package com.examen.practica.services.Impl;

import com.examen.practica.models.dto.UsuarioDto;
import com.examen.practica.models.dto.mapper.DtoMapperUsuario;
import com.examen.practica.models.entities.Role;
import com.examen.practica.models.entities.Usuario;
import com.examen.practica.repostories.RoleRepository;
import com.examen.practica.repostories.UsuarioRepository;
import com.examen.practica.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<UsuarioDto> obtenerUsuarios( ) {

        List<Usuario>  user = (List<Usuario>) usuarioRepository.findAll();
        return user
                .stream()
                .map(u-> DtoMapperUsuario
                        .builder()
                        .setUsuario(u)
                        .build())
                .collect(Collectors.toList());

    }
    @Override
    public Optional<UsuarioDto> encontrarUsuario(Long id) {
        return usuarioRepository.findById(id)
                .map(u-> DtoMapperUsuario
                        .builder()
                        .setUsuario(u)
                        .build());
    }

    @Override
    public UsuarioDto guardarUsuario(Usuario usuario) {
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));

        Optional<Role> o = roleRepository.findByName("ROLE_ADMIN");

        List<Role> roles = new ArrayList<>();
        if(o.isPresent()){
            roles.add(o.orElseThrow());
        }

        usuario.setRoles(roles);
        return DtoMapperUsuario.builder().setUsuario(usuarioRepository.save(usuario)).build();


    }
}
