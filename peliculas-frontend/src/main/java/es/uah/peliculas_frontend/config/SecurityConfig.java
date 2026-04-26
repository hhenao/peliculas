package es.uah.peliculas_frontend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // Para proyecto académico / microservicios
                .csrf(csrf -> csrf.disable())

                // Autorización de rutas
                .authorizeHttpRequests(auth -> auth

                        // Rutas públicas
                        .requestMatchers(
                                "/login",
                                "/register",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/peliculas/ranking"
                        ).permitAll()

                        // Solo ADMIN
                        .requestMatchers("/peliculas/crear",
                                "/peliculas/editar/**",
                                "/peliculas/eliminar/**",
                                "/peliculas/guardar",
                                "/peliculas/actualizar/**",
                                "/usuarios/**")
                        .hasRole("ADMIN")

                        // Crear crítica (solo USER)
                        .requestMatchers("/peliculas/*/critica")
                        .hasRole("USER")

                        // Eliminar crítica (solo ADMIN)
                        .requestMatchers("/criticas/eliminar/**")
                        .hasRole("ADMIN")

                        // USER y ADMIN
                        .requestMatchers("/peliculas/**")
                        .hasAnyRole("USER", "ADMIN")

                        // Todo lo demás requiere login
                        .anyRequest().authenticated()
                )

                // Login gestionado por Spring Security
                .formLogin(form -> form
                        .loginPage("/login")           // GET
                        .loginProcessingUrl("/login") // POST (automático)
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error")
                        .permitAll()
                )

                // Logout estándar
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
