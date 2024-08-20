package com.arrienda.proyecto.modelos;

import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity

public class Propiedad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombre;
    private String ubicacion;
    private boolean parqueadero;
    private boolean piscina;
    private ArrayList<Calificacion> calificaciones;
    private Arrendador propietario;
    private int cuartos;
    private int camas;
    private float area;
    private int capacidad;
    private ArrayList <Solicitud> solicitudes;
    private boolean disponible;
    private float precioXnoche;

}
