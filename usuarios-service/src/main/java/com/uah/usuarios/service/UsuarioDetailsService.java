package com.uah.usuarios.service;

import java.util.Collections;

import com.uah.usuarios.dto.UsuarioDTO;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import com.uah.usuarios.dao.UsuarioRepository;
import com.uah.usuarios.entity.UsuarioEntity;

@Service
public class UsuarioDetailsService implements UserDetailsService {

 private final UsuarioRepository repository;

    public UsuarioDetailsService(UsuarioRepository repository)
    {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
        throws UsernameNotFoundException
    {

        UsuarioEntity usuario = repository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .roles(usuario.getRole()) // ADMIN / USER
                .build();
    }

    public UsuarioDTO findByUsername(String username) {
        UsuarioEntity usuario = repository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return new UsuarioDTO(
                usuario.getAdminId(),
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.getRole()
        );
    }

}