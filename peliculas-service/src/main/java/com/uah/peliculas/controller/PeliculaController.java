package com.uah.peliculas.controller;

import com.uah.peliculas.dao.CriticaRepository;
import com.uah.peliculas.entity.ActorEntity;
import com.uah.peliculas.entity.PeliculaEntity;
import com.uah.peliculas.dao.ActorRepository;
import com.uah.peliculas.dao.PeliculaRepository;
import com.uah.peliculas.projection.PeliculaRankingProjection;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/peliculas")
public class PeliculaController {

    private final PeliculaRepository peliculaRepository;
    private final ActorRepository actorRepository;
    private final CriticaRepository criticaRepository;

    public PeliculaController(PeliculaRepository peliculaRepository,
                              ActorRepository actorRepository,
                              CriticaRepository criticaRepository) {
        this.peliculaRepository = peliculaRepository;
        this.actorRepository = actorRepository;
        this.criticaRepository = criticaRepository;
    }

    // ==========================
    // LISTAR
    // ==========================
    @GetMapping(produces = "application/json")
    public List<PeliculaEntity> listar() {
        return peliculaRepository.findAll();
    }

    // ==========================
    // OBTENER POR ID
    // ==========================
    @GetMapping("/{id}")
    public ResponseEntity<PeliculaEntity> obtener(@PathVariable Long id) {
        return peliculaRepository.findByIdWithActors(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ==========================
    // CREAR
    // ==========================
    @PostMapping
    public PeliculaEntity crear(@RequestBody PeliculaEntity pelicula) {

        if (pelicula.getActors() != null) {
            List<Long> ids = pelicula.getActors()
                    .stream()
                    .map(ActorEntity::getActorId)
                    .toList();

            List<ActorEntity> actores = actorRepository.findAllById(ids);
            pelicula.setActors(actores);
        }

        return peliculaRepository.save(pelicula);
    }


    // ==========================
    // ACTUALIZAR
    // ==========================
    @PutMapping("/{id}")
    public ResponseEntity<PeliculaEntity> actualizar(
            @PathVariable Long id,
            @RequestBody PeliculaEntity peliculaActualizada) {

        Optional<PeliculaEntity> optional = peliculaRepository.findById(id);

        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        PeliculaEntity pelicula = optional.get();

        pelicula.setTitulo(peliculaActualizada.getTitulo());
        pelicula.setAnio(peliculaActualizada.getAnio());
        pelicula.setDuracion(peliculaActualizada.getDuracion());
        pelicula.setPais(peliculaActualizada.getPais());
        pelicula.setDireccion(peliculaActualizada.getDireccion());
        pelicula.setGenero(peliculaActualizada.getGenero());
        pelicula.setSinopsis(peliculaActualizada.getSinopsis());
        pelicula.setImagenPortada(peliculaActualizada.getImagenPortada());

        return ResponseEntity.ok(peliculaRepository.save(pelicula));
    }


    // ==========================
    // ELIMINAR
    // ==========================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        if (!peliculaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        peliculaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{peliculaId}/asociar/{actorId}")
    @Transactional
    public ResponseEntity<Void> asociarActor(@PathVariable Long peliculaId,
                                             @PathVariable Long actorId) {

        PeliculaEntity pelicula = peliculaRepository
                .findByIdWithActors(peliculaId)
                .orElseThrow(() -> new RuntimeException("Película no encontrada"));

        ActorEntity actor = actorRepository
                .findById(actorId)
                .orElseThrow(() -> new RuntimeException("Actor no encontrado"));

        // Evitar duplicados
        if (!pelicula.getActors().contains(actor)) {
            pelicula.getActors().add(actor);
            peliculaRepository.save(pelicula);
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{peliculaId}/eliminarActor/{actorId}")
    @Transactional
    public ResponseEntity<Void> eliminarActor(@PathVariable Long peliculaId,
                                              @PathVariable Long actorId) {

        PeliculaEntity pelicula = peliculaRepository
                .findByIdWithActors(peliculaId)
                .orElseThrow(() -> new RuntimeException("Película no encontrada"));

        pelicula.getActors()
                .removeIf(a -> a.getActorId().equals(actorId));

        peliculaRepository.save(pelicula);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/ranking")
    public List<PeliculaRankingProjection> ranking() {
        return criticaRepository.peliculasConMejorNota();
    }
}
