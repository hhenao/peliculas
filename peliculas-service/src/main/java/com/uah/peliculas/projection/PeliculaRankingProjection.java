package com.uah.peliculas.projection;

public interface PeliculaRankingProjection {
    Long getIdPelicula();
    String getTitulo();
    String getGenero();
    Integer getAnio();
    String getDireccion();
    String getImagenPortada();
    Double getMedia();
}
