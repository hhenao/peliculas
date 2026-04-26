package com.uah.peliculas.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "actor")
public class ActorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "actor_id")
    private Long actorId;

    @Column(name = "nombre", length = 150, nullable = false)
    private String nombre;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "pais_nacimiento", length = 100)
    private String paisNacimiento;

    // ==========================
    // RELACIÓN INVERSA
    // ==========================
    @ManyToMany(mappedBy = "actors")
    @JsonIgnore
    private List<PeliculaEntity> peliculas = new ArrayList<>();

    // ==========================
    // GETTERS Y SETTERS
    // ==========================

    public Long getActorId() {
        return actorId;
    }

    public void setActorId(Long actorId) {
        this.actorId = actorId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getPaisNacimiento() {
        return paisNacimiento;
    }

    public void setPaisNacimiento(String paisNacimiento) {
        this.paisNacimiento = paisNacimiento;
    }

    public List<PeliculaEntity> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(List<PeliculaEntity> peliculas) {
        this.peliculas = peliculas;
    }
}
