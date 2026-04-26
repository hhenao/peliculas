package com.uah.usuarios.dto;

import jakarta.validation.constraints.*;

public class UsuarioRequestDTO {

    @NotBlank
    @Size(min = 3, max = 255)
    private String username;

    @NotBlank
    @Size(min = 3, max = 255)
    private String password;

    public String getUsername(){ return username;}
    public String getPassword(){return password;}
}
