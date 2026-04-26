package com.uah.usuarios.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "admin_user")
public class UsuarioEntity {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 @Column(name = "admin_id")
 private Long adminId;

 @Column(nullable = false, unique = true)
 private String username;

 @Column(nullable = false)
 private String password;

 @Column(nullable = false)
 private String role; // ADMIN / USER

 public Long getAdminId() { return adminId; }
 public void setAdminId(Long id) { this.adminId = id; }

 public String getUsername() { return username; }
 public void setUsername(String username) { this.username = username; }

 public String getPassword() { return password; }
 public void setPassword(String password) { this.password = password; }

    public String getRole() {
     return role;
    }
    public void setRole(String role){this.role = role;}
}