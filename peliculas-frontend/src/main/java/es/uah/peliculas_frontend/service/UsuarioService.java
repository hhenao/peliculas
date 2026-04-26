package es.uah.peliculas_frontend.service;

import es.uah.peliculas_frontend.client.UsuarioClient;
import es.uah.peliculas_frontend.model.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioClient client;

    public List<UsuarioDTO> listar() {
        return client.listar();
    }

    public UsuarioDTO findById(Long id) {
        return client.findById(id);
    }

    public void actualizar(Long id, UsuarioDTO usuario) {
        client.actualizar(id, usuario);
    }

    public void eliminar(Long id) {
        try {
            client.eliminar(id);
        } catch (HttpClientErrorException e) {
            throw new RuntimeException(e.getResponseBodyAsString());
        }
    }
}

