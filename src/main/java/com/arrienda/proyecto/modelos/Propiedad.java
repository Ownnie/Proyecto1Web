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
@SQLDelete(sql = "UPDATE propiedad SET status = 1 WHERE id=?")
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
    private long precioXnoche;
    protected int status;
    private long arrendadorId;
    private float calificionPromedio;

    @OneToMany
    private List<Calificacion> calificaciones;

    @OneToMany
    private List<Solicitud> solicitudes;

    private Boolean lavanderia;
    private Boolean wifi;
    private Boolean mascotas;
    private Boolean gimnasios;
    private Boolean zonaJuegos;
    private Boolean alimentacion;
    private String propiedad;
    private List<String> imagenes;

}
