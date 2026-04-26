package com.uah.peliculas.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pelicula")
public class PeliculaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pelicula_id")
    private Long idPelicula;

    @Column(name = "titulo", length = 200, nullable = false)
    private String titulo;

    @Column(name = "anio")
    private Integer anio;

    @Column(name = "duracion")
    private Integer duracion;

    @Column(name = "pais", length = 100)
    private String pais;

    @Column(name = "direccion", length = 200)
    private String direccion;

    @Column(name = "genero", length = 100)
    private String genero;

    @Lob
    @Column(name = "sinopsis", columnDefinition = "TEXT")
    private String sinopsis;

    @Column(name = "imagen_portada", length = 255)
    private String imagenPortada;

    // ==========================
    // RELACIÓN MANY TO MANY
    // ==========================
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "pelicula_actor",
            joinColumns = @JoinColumn(name = "pelicula_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private List<ActorEntity> actors = new ArrayList<>();

    @OneToMany(mappedBy = "pelicula", cascade = CascadeType.ALL)
    private List<CriticaEntity> criticas;

    // ==========================
    // GETTERS Y SETTERS
    // ==========================

    public Long getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(Long idPelicula) {
        this.idPelicula = idPelicula;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getImagenPortada() {
        return imagenPortada;
    }

    public void setImagenPortada(String imagenPortada) {
        this.imagenPortada = imagenPortada;
    }

    public List<ActorEntity> getActors() {
        return actors;
    }

    public void setActors(List<ActorEntity> actors) {
        this.actors = actors;
    }
}
