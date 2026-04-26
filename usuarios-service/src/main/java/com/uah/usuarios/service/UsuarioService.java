package com.uah.usuarios.service;

import com.uah.usuarios.dao.UsuarioRepository;
import com.uah.usuarios.dto.UsuarioDTO;
import com.uah.usuarios.entity.UsuarioEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    // LISTAR
    public List<UsuarioDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(u -> new UsuarioDTO(
                        u.getAdminId(),
                        u.getUsername(),
                        u.getPassword(),
                        u.getRole()
                ))
                .collect(Collectors.toList());
    }

    // BUSCAR POR ID
    public UsuarioDTO findById(Long id) {
        UsuarioEntity usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return new UsuarioDTO(
                usuario.getAdminId(),
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.getRole()
        );
    }

    // ACTUALIZAR (sin tocar password)
    public UsuarioDTO update(Long id, UsuarioDTO dto) {

        UsuarioEntity usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setUsername(dto.getUsername());
        usuario.setRole(dto.getRole());

        repository.save(usuario);

        return new UsuarioDTO(
                usuario.getAdminId(),
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.getRole()
        );
    }

    // ELIMINAR
    public void delete(Long id) {

        UsuarioEntity usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if ("ADMIN".equalsIgnoreCase(usuario.getUsername())) {
            throw new RuntimeException("No se puede eliminar el usuario ADMIN principal");
        }

        repository.delete(usuario);
    }

}
