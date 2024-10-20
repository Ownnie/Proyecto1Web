package com.arrienda.proyecto.modelos;

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
    private float calificionPromedio;
    protected int status;

    @Enumerated(EnumType.STRING)
    private Rol rol;
    
    public enum Rol {
        ARRENDADOR, ARRENDATARIO
    }

}
