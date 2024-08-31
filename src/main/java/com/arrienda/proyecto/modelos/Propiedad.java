package com.arrienda.proyecto.modelos;

import java.util.List;

import org.hibernate.annotations.*;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "status = 0")
@SQLDelete(sql = "UPDATE application SET status = 1 WHERE id=?")
public class Propiedad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nombre;
    private String ubicacion;
    private boolean parqueadero;
    private boolean piscina;
    private int cuartos;
    private int camas;
    private float area;
    private int capacidad;
    private boolean disponible;
    private float precioXnoche;
    protected int status;

    @OneToMany
    private List<Calificacion> calificaciones;

    @ManyToOne
    private Arrendador propietario;

    @OneToMany
    private List<Solicitud> solicitudes;

}
