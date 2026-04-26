package es.uah.peliculas_frontend.controller;

import es.uah.peliculas_frontend.model.dto.ActorDTO;
import es.uah.peliculas_frontend.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/actores")
public class ActorWebController {

    @Autowired
    private ActorService actorService;

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("actores", actorService.listar());
        return "actores/list";
    }

    @GetMapping("/crear")
    public String crear(Model model) {
        model.addAttribute("actor", new ActorDTO());
        return "actores/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute ActorDTO dto) {
        actorService.guardar(dto);
        return "redirect:/actores/listar";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        ActorDTO dto = actorService.buscarPorId(id);

        if (dto == null) {
            // Manejo de error opcional
            return "redirect:/actores";
        }

        model.addAttribute("actor", dto);
        return "actores/form";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(
            @PathVariable Long id,
            @ModelAttribute ActorDTO dto) {
        actorService.actualizar(id,dto);
        return "redirect:/actores/listar";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        actorService.eliminar(id);
        return "redirect:/actores/listar";
    }
}
