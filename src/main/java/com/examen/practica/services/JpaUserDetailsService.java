package com.examen.practica.services;

import com.examen.practica.models.entities.Usuario;
import com.examen.practica.repostories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> o = usuarioRepository.findByNombre(username);

        if(!o.isPresent()){
            throw new UsernameNotFoundException(String.format("Username %s no existe en el sistema!", username));
        }

        Usuario usuario = o.orElseThrow();

        List<GrantedAuthority> authorities = usuario.getRoles()
                .stream()
                .map(r-> new SimpleGrantedAuthority(r.getName()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(
                usuario.getNombre(),
                usuario.getContrasena(),
                true,
                true,
                true,
                true,
                authorities
        );
    }
}