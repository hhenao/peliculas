package com.uah.usuarios.dto;

import jakarta.validation.constraints.*;

public class UsuarioDTO {
    private Long adminId;
    private String username;
    private String password;
    private String role;

    public UsuarioDTO(Long adminId, String username, String password, String role) {
        this.adminId = adminId;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole(){return role;}

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password =  password;
    }

    public void setRole(String role){this.role = role;}
}

