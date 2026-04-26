package es.uah.peliculas_frontend.model.dto;

public class UsuarioRequestDTO {

    private String username;
    private String password;

    public UsuarioRequestDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password =  password;
    }
}

