package com.arrienda.proyecto.modelos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String usuario;
    private String contrasena;
    private String nombre;
    private String correo;

    public Usuario(String usuario, String contrasena, String nombre, String correo, Rol rol, int status) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.correo = correo;
        this.rol = rol;
        this.status = status;
    }

    @Enumerated(EnumType.STRING)
    private Rol rol;

    protected int status;

    public enum Rol {
        ARRENDADOR, ARRENDATARIO
    }

    @Override
    public String getUsername() {
        return usuario;
    }

    @Override
    public String getPassword() {
        return contrasena;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + rol.name()));
        return authorities;
    }
}
