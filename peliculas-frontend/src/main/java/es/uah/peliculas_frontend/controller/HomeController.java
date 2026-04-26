package es.uah.peliculas_frontend.controller;

import es.uah.peliculas_frontend.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    private PeliculaService peliculaService;

    @GetMapping({"/", "/index"})
    public String index(Model model)
    {
        model.addAttribute("ranking",
                peliculaService.ranking());
        return "index";
    }
}
