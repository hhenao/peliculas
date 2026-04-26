package com.uah.peliculas.controller;

import com.uah.peliculas.entity.ActorEntity;
import com.uah.peliculas.dao.ActorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/actores")
public class ActorController {

    private final ActorRepository actorRepository;

    public ActorController(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    // LISTAR
    @GetMapping
    public List<ActorEntity> listar() {
        return actorRepository.findAll();
    }

    // OBTENER POR ID
    @GetMapping("/{id}")
    public ResponseEntity<ActorEntity> obtener(@PathVariable Long id) {
        return actorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CREAR
    @PostMapping
    public ActorEntity crear(@RequestBody ActorEntity actor) {
        return actorRepository.save(actor);
    }

    // ACTUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<ActorEntity> actualizar(
            @PathVariable Long id,
            @RequestBody ActorEntity actorActualizado) {

        Optional<ActorEntity> optional = actorRepository.findById(id);

        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ActorEntity actor = optional.get();
        actor.setNombre(actorActualizado.getNombre());
        actor.setFechaNacimiento(actorActualizado.getFechaNacimiento());
        actor.setPaisNacimiento(actorActualizado.getPaisNacimiento());

        return ResponseEntity.ok(actorRepository.save(actor));
    }

    // ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        if (!actorRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        actorRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

