package es.uah.peliculas_frontend.service;

import es.uah.peliculas_frontend.client.ActorClient;
import es.uah.peliculas_frontend.client.PeliculaClient;
import es.uah.peliculas_frontend.model.dto.ActorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ActorService {

    @Autowired
    private ActorClient client;

    private final String BASE_URL = "http://localhost:8081/api/actores";

    public List<ActorDTO> listar() {
        return client.listar();
    }

    public ActorDTO buscarPorId(Long id) {
        return client.buscarPorId(id);
    }

    public void guardar(ActorDTO dto) {
        client.guardar(dto);
    }

    public void actualizar(Long id, ActorDTO dto) {
        ActorDTO dtoInit = client.buscarPorId(id);

        dtoInit.setActorId(dto.getActorId());
        dtoInit.setNombre(dto.getNombre());
        dtoInit.setFechaNacimiento(dto.getFechaNacimiento());
        dtoInit.setPaisNacimiento(dto.getPaisNacimiento());

        client.actualizar(dtoInit.getActorId(),dtoInit);
    }

    public void eliminar(Long id) {
        client.eliminar(id);
    }

    public List<ActorDTO> buscarPorIds(List<Long> ids) {
        List<ActorDTO> actores = new ArrayList<>();
        for (Long id : ids) {
            actores.add(buscarPorId(Long.valueOf(id)));
        }
        return actores;
    }

}

