package com.arrienda.proyecto.modelos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

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
@Where(clause = "status = 0")
@SQLDelete(sql = "UPDATE arrendatario SET status = 1 WHERE id=?")
public class Usuario {

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

}
