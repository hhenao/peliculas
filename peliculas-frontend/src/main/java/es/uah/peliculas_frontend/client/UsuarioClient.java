package es.uah.peliculas_frontend.client;

import es.uah.peliculas_frontend.model.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class UsuarioClient {

    @Autowired
    private RestTemplate restTemplate;

    private final String URL = "http://localhost:8081/api/usuarios";

    public List<UsuarioDTO> listar() {
        return Arrays.asList(
                restTemplate.getForObject(URL, UsuarioDTO[].class)
        );
    }

    public UsuarioDTO findById(Long id) {
        return restTemplate.getForObject(URL + "/" + id, UsuarioDTO.class);
    }

    public void actualizar(Long id, UsuarioDTO usuario) {
        restTemplate.put(URL + "/" + id, usuario);
    }

    public void eliminar(Long id) {
        restTemplate.delete(URL + "/" + id);
    }
}

