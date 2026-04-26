package es.uah.peliculas_frontend.client;

import es.uah.peliculas_frontend.model.dto.CriticaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class CriticaClient {

    @Autowired
    private RestTemplate restTemplate;

    private final String URL = "http://localhost:8081/api/criticas";

    // 🔹 Listar críticas por película
    public List<CriticaDTO> listarPorPelicula(Long peliculaId) {

        return restTemplate.exchange(
                URL + "/" + peliculaId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CriticaDTO>>() {}
        ).getBody();
    }

    // 🔹 Obtener nota media
    public Double obtenerMedia(Long peliculaId) {
        Double media = restTemplate.getForObject(
                URL + "/media/" + peliculaId,
                Double.class);

        return media != null ? media : 0.0;
    }

    // 🔹 Crear crítica
    public void crearCritica(Long peliculaId, Long adminId, String username, CriticaDTO critica) {

        critica.setAdminUserId(adminId);
        critica.setUsername(username);

        restTemplate.postForObject(
                URL + "/" + peliculaId + "/" + adminId,
                critica,
                CriticaDTO.class
        );
    }

    // 🔹 Eliminar crítica (ADMIN)
    public void eliminarCritica(Long criticaId) {

        restTemplate.delete(
                URL + "/" + criticaId
        );
    }
}
