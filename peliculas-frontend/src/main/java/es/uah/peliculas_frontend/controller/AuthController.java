package es.uah.peliculas_frontend.controller;

import es.uah.peliculas_frontend.model.dto.UsuarioRequestDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final RestTemplate restTemplate;

    public AuthController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // =========================
    // VISTAS
    // =========================

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String register() {
        return "auth/register";
    }

    // =========================
    // REGISTRO (SÍ SE MANTIENE)
    // =========================

    @PostMapping("/register")
    public String doRegister(
            @RequestParam String username,
            @RequestParam String password,
            RedirectAttributes redirectAttributes) {

        UsuarioRequestDTO dto = new UsuarioRequestDTO(username, password);

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<UsuarioRequestDTO> request =
                    new HttpEntity<>(dto, headers);

            // SIEMPRE contra el API Gateway
            restTemplate.postForEntity(
                    "http://localhost:8081/api/usuarios/register",
                    request,
                    Void.class
            );

            // registro correcto → ir a login
            return "redirect:/login";

        } catch (HttpClientErrorException e) {
            redirectAttributes.addAttribute("error", "");
            redirectAttributes.addAttribute("status", e.getStatusCode().value());
            return "redirect:/register";
        }
    }
}
