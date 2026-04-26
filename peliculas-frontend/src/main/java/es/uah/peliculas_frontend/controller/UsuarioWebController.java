package es.uah.peliculas_frontend.controller;

import es.uah.peliculas_frontend.model.dto.UsuarioDTO;
import es.uah.peliculas_frontend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios")
public class UsuarioWebController {

    @Autowired
    private UsuarioService service;

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("usuarios", service.listar());
        return "usuarios/list";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("usuario", service.findById(id));
        return "usuarios/form";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable Long id,
                             @RequestParam String username,
                             @RequestParam String role) {

        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setUsername(username);
        usuario.setRole(role);

        service.actualizar(id, usuario);

        return "redirect:/usuarios/listar";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id,
                           RedirectAttributes redirectAttributes) {

        try {
            service.eliminar(id);
            redirectAttributes.addFlashAttribute("success",
                    "Usuario eliminado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    e.getMessage());
        }

        return "redirect:/usuarios/listar";
    }

}

