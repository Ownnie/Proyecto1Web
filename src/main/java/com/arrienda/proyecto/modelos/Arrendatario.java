package com.arrienda.proyecto.modelos;

import java.util.ArrayList;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "status = 0")
@SQLDelete(sql = "UPDATE application SET status = 1 WHERE id=?")
public class Arrendatario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    protected String nombre;
    protected String usuario;
    protected String contrasena;
    protected int status;

    @OneToMany(mappedBy = "arrendatario")
    private ArrayList<Solicitud> solicitudes;

    @OneToMany
    protected ArrayList<Calificacion> calificaciones;

}
