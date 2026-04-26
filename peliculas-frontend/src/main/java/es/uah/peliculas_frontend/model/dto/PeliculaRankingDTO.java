package es.uah.peliculas_frontend.model.dto;

public class PeliculaRankingDTO {

    private Long idPelicula;
    private String titulo;
    private String genero;
    private Integer anio;
    private String direccion;
    private String imagenPortada;
    private Double media;

    public PeliculaRankingDTO(Long idPelicula,
                              String titulo,
                              String genero,
                              Integer anio,
                              String direccion,
                              String imagenPortada,
                              Double media) {
        this.idPelicula = idPelicula;
        this.titulo = titulo;
        this.genero = genero;
        this.anio = anio;
        this.direccion = direccion;
        this.imagenPortada = imagenPortada;
        this.media = media;
    }

    // getters

    public Long getIdPelicula() {
        return idPelicula;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getGenero() {
        return genero;
    }

    public Integer getAnio() {
        return anio;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getImagenPortada() {
        return imagenPortada;
    }

    public Double getMedia() {
        return media;
    }
}

