package com.uah.peliculas.controller;

import com.uah.peliculas.dao.CriticaRepository;
import com.uah.peliculas.dao.PeliculaRepository;
import com.uah.peliculas.entity.CriticaEntity;
import com.uah.peliculas.entity.PeliculaEntity;
import com.uah.peliculas.model.dto.PeliculaRankingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/criticas")
public class CriticaController {

    @Autowired
    private CriticaRepository criticaRepository;

    @Autowired
    private PeliculaRepository peliculaRepository;

    // 🔹 Crear crítica
    @PostMapping("/{peliculaId}/{adminUserId}")
    public ResponseEntity<?> crearCritica(
            @PathVariable Long peliculaId,
            @PathVariable Long adminUserId,
            @RequestBody CriticaEntity critica) {

        // Validar si ya existe crítica del usuario
        if (criticaRepository.existsByAdminUserIdAndPelicula_IdPelicula(adminUserId, peliculaId)) {
            return ResponseEntity.badRequest()
                    .body("El usuario ya ha realizado una crítica para esta película");
        }

        PeliculaEntity pelicula = peliculaRepository.findById(peliculaId)
                .orElse(null);

        if (pelicula == null) {
            return ResponseEntity.notFound().build();
        }

        critica.setPelicula(pelicula);
        critica.setAdminUserId(adminUserId);
        critica.setFecha(LocalDate.now());

        CriticaEntity nueva = criticaRepository.save(critica);

        return ResponseEntity.ok(nueva);
    }

    // 🔹 Listar críticas por película
    @GetMapping("/{peliculaId}")
    public ResponseEntity<List<CriticaEntity>> listarCriticas(@PathVariable Long peliculaId) {
        return ResponseEntity.ok(
                criticaRepository.findByPelicula_IdPelicula(peliculaId)
        );
    }

    // 🔹 Obtener nota media
    @GetMapping("/media/{peliculaId}")
    public ResponseEntity<Double> notaMedia(@PathVariable Long peliculaId) {
        Double media = criticaRepository.calcularNotaMedia(peliculaId);
        return ResponseEntity.ok(media != null ? media : 0.0);
    }

    // 🔹 Eliminar crítica (ADMIN)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        criticaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }



}
