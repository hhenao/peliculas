package es.uah.peliculas_frontend.controller;

import es.uah.peliculas_frontend.model.dto.ActorDTO;
import es.uah.peliculas_frontend.model.dto.CriticaDTO;
import es.uah.peliculas_frontend.model.dto.PeliculaDTO;
import es.uah.peliculas_frontend.model.dto.PeliculaFormDTO;
import es.uah.peliculas_frontend.service.ActorService;
import es.uah.peliculas_frontend.service.CriticaService;
import es.uah.peliculas_frontend.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("/peliculas")
public class PeliculaWebController {

    @Autowired
    private PeliculaService peliculaService;

    @Autowired
    private ActorService actorService;

    @Autowired
    private CriticaService criticaService;

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("peliculas", peliculaService.listar());
        return "peliculas/list";
    }

    @GetMapping("/crear")
    public String crear(Model model) {
        model.addAttribute("pelicula", new PeliculaFormDTO());
        model.addAttribute("actores", actorService.listar());
        model.addAttribute("accion", "/peliculas/guardar");
        return "peliculas/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute PeliculaFormDTO pelicula,
                          @RequestParam(value = "archivoImagen", required = false) MultipartFile archivoImagen) {
        peliculaService.guardar(pelicula, archivoImagen);
        return "redirect:/peliculas/listar";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") Integer id, Model model) {

        PeliculaDTO peliculaDTO = peliculaService.buscarPorId(Long.valueOf(id));

        PeliculaFormDTO formDTO = new PeliculaFormDTO();

        formDTO.setIdPelicula(peliculaDTO.getIdPelicula());
        formDTO.setTitulo(peliculaDTO.getTitulo());
        formDTO.setAnio(peliculaDTO.getAnio());
        formDTO.setDuracion(peliculaDTO.getDuracion());
        formDTO.setPais(peliculaDTO.getPais());
        formDTO.setDireccion(peliculaDTO.getDireccion());
        formDTO.setGenero(peliculaDTO.getGenero());
        formDTO.setSinopsis(peliculaDTO.getSinopsis());
        formDTO.setImagenPortada(peliculaDTO.getImagenPortada());

        // Convertir lista de actores a lista de IDs
        if (peliculaDTO.getActors() != null) {
            formDTO.setActoresIds(
                    peliculaDTO.getActors()
                            .stream()
                            .map(ActorDTO::getActorId)
                            .toList()
            );
        }

        formDTO.setActors(peliculaDTO.getActors());

        model.addAttribute("pelicula", formDTO);
        model.addAttribute("actores", actorService.listar());
        model.addAttribute("accion", "/peliculas/actualizar/" + id);
        return "peliculas/form";
    }

    @PostMapping("/{id}/asociar")
    public String asociarActor(@PathVariable Long id,
                               @RequestParam Long actorId) {

        peliculaService.asociarActor(id, actorId);

        return "redirect:/peliculas/editar/" + id;
    }

    @PostMapping("/{id}/eliminarActor")
    public String eliminarActor(@PathVariable Long id,
                                @RequestParam Long actorId) {

        peliculaService.eliminarActor(id, actorId);

        return "redirect:/peliculas/editar/" + id;
    }



    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable("id") Long id,
                             @ModelAttribute("pelicula") PeliculaFormDTO formDTO,
                             @RequestParam("archivoImagen") MultipartFile archivoImagen) {

        formDTO.setIdPelicula(id);

        peliculaService.actualizar(formDTO, archivoImagen);

        return "redirect:/peliculas/listar";
    }


    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Long id) {
        peliculaService.eliminar(id);
        return "redirect:/peliculas/listar";
    }

    @GetMapping("/ficha/{id}")
    public String ficha(@PathVariable("id") Long id, Model model) {

        PeliculaDTO pelicula = peliculaService.buscarPorId(id);
        model.addAttribute("pelicula", pelicula);
        model.addAttribute("criticas", criticaService.listarPorPelicula(id));
        model.addAttribute("notaMedia", criticaService.obtenerNotaMedia(id));

        return "peliculas/ficha";
    }

    @GetMapping("/buscar")
    public String buscar(@RequestParam(required = false) String titulo,
                         @RequestParam(required = false) String genero,
                         @RequestParam(required = false) String actor,
                         Model model) {

        List<PeliculaDTO> peliculas = peliculaService.buscar(titulo, genero, actor);

        model.addAttribute("peliculas", peliculas);

        return "peliculas/list";
    }

    @PostMapping("/{id}/critica")
    public String crearCritica(
            @PathVariable Long id,
            @RequestParam Integer nota,
            @RequestParam String descripcion,
            Model model) {

        CriticaDTO critica = new CriticaDTO();
        critica.setNota(nota);
        critica.setDescripcion(descripcion);

        String error = criticaService.crearCritica(id, critica);

        if (error != null) {
            model.addAttribute("errorCritica", error);
            return ficha(id, model);  // volver a cargar ficha con error
        }

        return "redirect:/peliculas/ficha/" + id;
    }

    @PostMapping("/{peliculaId}/criticas/eliminar/{criticaId}")
    public String eliminarCritica(@PathVariable Long peliculaId,
                                  @PathVariable Long criticaId) {

        criticaService.eliminarCritica(criticaId);

        return "redirect:/peliculas/ficha/" + peliculaId;
    }


}
