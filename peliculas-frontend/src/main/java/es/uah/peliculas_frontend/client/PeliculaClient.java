package es.uah.peliculas_frontend.client;

import es.uah.peliculas_frontend.model.dto.PeliculaDTO;
import es.uah.peliculas_frontend.model.dto.PeliculaRankingDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class PeliculaClient {

    private final RestTemplate rest = new RestTemplate();
    private final String BASE_URL = "http://localhost:8081/api/peliculas";

    // -------------------
    //      PELICULAS
    // -------------------

    public List<PeliculaDTO> listar() {
        return Arrays.asList(rest.getForObject(BASE_URL, PeliculaDTO[].class));
    }

    public PeliculaDTO buscarPorId(Long id) {
        return rest.getForObject(BASE_URL + "/" + id, PeliculaDTO.class);
    }

    public void guardar(PeliculaDTO dto) {
        rest.postForObject(BASE_URL, dto, Void.class);
    }

    public void actualizar(Long id, PeliculaDTO dto) {
        rest.put(BASE_URL + "/" + id, dto);
    }

    public void eliminar(Long id) {
        rest.delete(BASE_URL + "/" + id);
    }

    public void asociarActor(Long peliculaId, Long actorId) {
        rest.postForObject(BASE_URL + "/" + peliculaId + "/asociar/" + actorId, null, Void.class);
    }

    public void eliminarActor(Long peliculaId, Long actorId) {
        rest.postForObject(BASE_URL + "/" + peliculaId + "/eliminarActor/" + actorId, null, Void.class);
    }

    public List<PeliculaDTO> buscar(String titulo, String genero, String actor) {

        String url = BASE_URL + "/buscar?";

        if (titulo != null && !titulo.isEmpty()) {
            url += "titulo=" + titulo + "&";
        }
        if (genero != null && !genero.isEmpty()) {
            url += "genero=" + genero + "&";
        }
        if (actor != null && !actor.isEmpty()) {
            url += "actor=" + actor + "&";
        }

        // Quitar el último "&" si existe
        if (url.endsWith("&")) {
            url = url.substring(0, url.length() - 1);
        }

        return Arrays.asList(
                rest.getForObject(url, PeliculaDTO[].class)
        );
    }

    public List<PeliculaRankingDTO> ranking() {
        return Arrays.asList(
                rest.getForObject(
                        BASE_URL + "/ranking",
                        PeliculaRankingDTO[].class
                )
        );
    }


}
