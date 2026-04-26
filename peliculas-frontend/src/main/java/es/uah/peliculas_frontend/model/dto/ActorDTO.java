package es.uah.peliculas_frontend.model.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class ActorDTO {
    private Long actorId;
    private String nombre;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;
    private String paisNacimiento;
}
