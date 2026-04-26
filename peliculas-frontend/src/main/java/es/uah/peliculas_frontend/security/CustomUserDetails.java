package es.uah.peliculas_frontend.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomUserDetails extends org.springframework.security.core.userdetails.User {

    private Long adminId;
    private String role;

    public CustomUserDetails(Long adminId,
                             String username,
                             String password,
                             String role,
                             Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.adminId = adminId;
        this.role = role;
    }

    public Long getAdminId() {
        return adminId;
    }

    public String getRole() {
        return role;
    }
}

