package com.uah.peliculas.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import com.uah.peliculas.entity.PeliculaEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PeliculaRepository extends JpaRepository<PeliculaEntity,Long>
{
    @Query("SELECT p FROM PeliculaEntity p LEFT JOIN FETCH p.actors WHERE p.idPelicula = :id")
    Optional<PeliculaEntity> findByIdWithActors(@Param("id") Long id);

}