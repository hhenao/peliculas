package es.uah.peliculas_frontend.service;

import es.uah.peliculas_frontend.client.CriticaClient;
import es.uah.peliculas_frontend.model.dto.CriticaDTO;
import es.uah.peliculas_frontend.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class CriticaService {

    @Autowired
    private CriticaClient criticaClient;

    // 🔹 Listar críticas de una película
    public List<CriticaDTO> listarPorPelicula(Long peliculaId) {
        return criticaClient.listarPorPelicula(peliculaId);
    }

    // 🔹 Obtener nota media
    public Double obtenerNotaMedia(Long peliculaId) {
        Double media = criticaClient.obtenerMedia(peliculaId);
        return media != null ? media : 0.0;
    }

    // 🔹 Crear crítica usando el usuario autenticado
    public String crearCritica(Long peliculaId, CriticaDTO critica) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails)) {
            throw new RuntimeException("Usuario no autenticado");
        }

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        Long adminId = userDetails.getAdminId();
        String username = userDetails.getUsername();
        try
        {

            criticaClient.crearCritica(
                    peliculaId,
                    adminId,
                    username,
                    critica
            );
            return null;
        } catch (HttpClientErrorException.BadRequest ex) {
            return ex.getResponseBodyAsString(); // mensaje del backend
        }
    }

    // 🔹 Eliminar crítica (para ADMIN)
    public void eliminarCritica(Long criticaId) {
        criticaClient.eliminarCritica(criticaId);
    }
}
