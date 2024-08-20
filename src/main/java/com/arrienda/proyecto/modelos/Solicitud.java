package com.arrienda.proyecto.modelos;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity

public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Date fechaLlegada; 
    private Date fechaPartida; 
    private Propiedad propiedad;
    private Arrendatario arrendatario;
    private boolean aceptacion;
    private int cantidadPersonas;

}
