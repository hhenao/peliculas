package es.uah.peliculas_frontend.service;

import es.uah.peliculas_frontend.client.PeliculaClient;
import es.uah.peliculas_frontend.model.dto.ActorDTO;
import es.uah.peliculas_frontend.model.dto.PeliculaDTO;
import es.uah.peliculas_frontend.model.dto.PeliculaFormDTO;
import es.uah.peliculas_frontend.model.dto.PeliculaRankingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class PeliculaService {

    @Autowired
    private PeliculaClient client;

    @Autowired
    private ActorService actorService;

    // LISTAR
    public List<PeliculaDTO> listar() {
        return client.listar();
    }

    // BUSCAR
    public PeliculaDTO buscarPorId(Long id) {
        return client.buscarPorId(id);
    }

    // GUARDAR
    public void guardar(PeliculaFormDTO form, MultipartFile archivoImagen) {

        PeliculaDTO dto = new PeliculaDTO();

        //Campos simples
        dto.setTitulo(form.getTitulo());
        dto.setAnio(form.getAnio());
        dto.setDuracion(form.getDuracion());
        dto.setPais(form.getPais());
        dto.setDireccion(form.getDireccion());
        dto.setGenero(form.getGenero());
        dto.setSinopsis(form.getSinopsis());

        //Actores
        if (form.getActoresIds() != null) {
            List<ActorDTO> actores = actorService.buscarPorIds(form.getActoresIds());
            dto.setActors(actores);
        }

        //Imagen
        try {
            if (archivoImagen != null && !archivoImagen.isEmpty()) {

                String nombreArchivo = System.currentTimeMillis() + "_" +
                        archivoImagen.getOriginalFilename();

                Path ruta = Paths.get("src/main/resources/static/img/peliculas/" + nombreArchivo);

                Files.write(ruta, archivoImagen.getBytes());

                dto.setImagenPortada(nombreArchivo);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error guardando imagen", e);
        }

        //Enviar al backend
        client.guardar(dto);
    }


    // ACTUALIZAR
    public void actualizar(PeliculaFormDTO form, MultipartFile archivoImagen) {

        // 1. Obtener película original del backend
        PeliculaDTO dto = client.buscarPorId(form.getIdPelicula());

        // 2. Actualizar campos simples
        dto.setTitulo(form.getTitulo());
        dto.setAnio(form.getAnio());
        dto.setDuracion(form.getDuracion());
        dto.setPais(form.getPais());
        dto.setDireccion(form.getDireccion());
        dto.setGenero(form.getGenero());
        dto.setSinopsis(form.getSinopsis());

        // 3. Convertir actoresIds en List<ActorDTO>
        if (form.getActoresIds() != null) {
            List<ActorDTO> actores = actorService.buscarPorIds(form.getActoresIds());
            dto.setActors(actores);
        } else {
            dto.setActors(new ArrayList<>());
        }

        // 4. Actualizar imagen si se cargó nueva
        try {
            if (archivoImagen != null && !archivoImagen.isEmpty()) {

                String nombreArchivo = System.currentTimeMillis() + "_" +
                        archivoImagen.getOriginalFilename();

                Path ruta = Paths.get("src/main/resources/static/img/peliculas/" + nombreArchivo);

                Files.write(ruta, archivoImagen.getBytes());

                dto.setImagenPortada(nombreArchivo);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 5. Enviar actualización al backend
        client.actualizar(dto.getIdPelicula(), dto);
    }

    // ELIMINAR
    public void eliminar(Long id) {
        client.eliminar(id);
    }

    // BUSCAR con filtros (título, género, actor)
    public List<PeliculaDTO> buscar(String titulo, String genero, String actor) {
        return client.buscar(titulo, genero, actor);
    }

    public void asociarActor(Long peliculaId, Long actorId) {
        client.asociarActor(peliculaId,actorId);
    }

    public void eliminarActor(Long peliculaId, Long actorId) {
        client.eliminarActor(peliculaId,actorId);
    }

    public List<PeliculaRankingDTO> ranking() {
        return client.ranking();
    }


}
