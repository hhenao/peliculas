package com.uah.peliculas.dao;

import com.uah.peliculas.entity.CriticaEntity;
import com.uah.peliculas.model.dto.PeliculaRankingDTO;
import com.uah.peliculas.projection.PeliculaRankingProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CriticaRepository extends JpaRepository<CriticaEntity, Long> {

    List<CriticaEntity> findByPelicula_IdPelicula(Long idPelicula);

    boolean existsByAdminUserIdAndPelicula_IdPelicula(Long adminUserId, Long idPelicula);

    @Query("SELECT AVG(c.nota) FROM CriticaEntity c WHERE c.pelicula.idPelicula = :idPelicula")
    Double calcularNotaMedia(@Param("idPelicula") Long idPelicula);

    @Query("""
SELECT 
    p.idPelicula as idPelicula,
    p.titulo as titulo,
    p.genero as genero,
    p.anio as anio,
    p.direccion as direccion,
    p.imagenPortada as imagenPortada,
    AVG(c.nota) as media
FROM CriticaEntity c
JOIN c.pelicula p
GROUP BY p.idPelicula, p.titulo, p.genero, p.anio, p.direccion, p.imagenPortada
ORDER BY AVG(c.nota) DESC
""")
    List<PeliculaRankingProjection> peliculasConMejorNota();

}

