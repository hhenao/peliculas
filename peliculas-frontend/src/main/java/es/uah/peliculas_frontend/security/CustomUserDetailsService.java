package es.uah.peliculas_frontend.security;

import es.uah.peliculas_frontend.model.dto.UsuarioDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final RestTemplate restTemplate;

    public CustomUserDetailsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        String url = "http://localhost:8081/api/usuarios/login/" + username;

        ResponseEntity<UsuarioDTO> response =
                restTemplate.getForEntity(url, UsuarioDTO.class);

        if (!response.getStatusCode().is2xxSuccessful()
                || response.getBody() == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        UsuarioDTO usuario = response.getBody();

        return new CustomUserDetails(
                usuario.getAdminId(),
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.getRole(),
                List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRole())));
    }
}

