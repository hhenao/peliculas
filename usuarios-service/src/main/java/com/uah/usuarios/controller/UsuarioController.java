package com.uah.usuarios.controller;

import java.util.List;

import com.uah.usuarios.dto.UsuarioDTO;
import com.uah.usuarios.dto.UsuarioRequestDTO;
import com.uah.usuarios.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.uah.usuarios.dao.UsuarioRepository;
import com.uah.usuarios.entity.UsuarioEntity;
import com.uah.usuarios.service.UsuarioDetailsService;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository repository;
    private final UsuarioDetailsService usuarioDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioRepository repository,
                             UsuarioDetailsService usuarioDetailsService,
                             UsuarioService usuarioService,
                             PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.usuarioDetailsService = usuarioDetailsService;
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public List<UsuarioEntity> listar() {
        return repository.findAll();
    }

    @GetMapping("/login/{username}")
    public ResponseEntity<UsuarioDTO> getByUsername(@PathVariable String username) {
        UsuarioDTO usuario = usuarioDetailsService.findByUsername(username);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UsuarioRequestDTO dto){
        //Validacion de duplicado
        if (repository.existsByUsername(dto.getUsername())){
            // 409 - Conflict CON MENSAJE CLARO
            throw new ResponseStatusException(HttpStatus.CONFLICT,"El usuario a registrar ya existe");
        }

        //Se crea la entidad
        UsuarioEntity entity = new UsuarioEntity();
        entity.setUsername(dto.getUsername());

        String hash = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt());
        entity.setPassword(hash);

        entity.setRole("USER");

        repository.save(entity);

        //Respuesta 201 Create sin body
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado correctamente.");
    }

    @GetMapping("/{id}")
    public UsuarioDTO findById(@PathVariable Long id) {
        return usuarioService.findById(id);
    }

    @PutMapping("/{id}")
    public UsuarioDTO actualizar(@PathVariable Long id,
                                 @RequestBody UsuarioDTO dto) {
        return usuarioService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            usuarioService.delete(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}