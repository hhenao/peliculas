package es.uah.peliculas_frontend.client;

import es.uah.peliculas_frontend.model.dto.ActorDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class ActorClient {

    private final RestTemplate rest = new RestTemplate();
    private final String BASE_URL = "http://localhost:8081/api/actores";

    // -------------------
    //      ACTORES
    // -------------------

    public List<ActorDTO> listar() {
        return Arrays.asList(rest.getForObject(BASE_URL, ActorDTO[].class));
    }

    public ActorDTO buscarPorId(Long id) {
        return rest.getForObject(BASE_URL + "/" + id, ActorDTO.class);
    }

    public void guardar(ActorDTO dto) {
        rest.postForObject(BASE_URL, dto, Void.class);
    }

    public void actualizar(Long id, ActorDTO dto) {
        rest.put(BASE_URL + "/" + id, dto);
    }

    public void eliminar(Long id) {
        rest.delete(BASE_URL + "/" + id);
    }

}
