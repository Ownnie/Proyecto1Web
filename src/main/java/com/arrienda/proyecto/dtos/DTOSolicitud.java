package com.arrienda.proyecto.dtos;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DTOSolicitud {
    private long id;
    private Date fechaLlegada;
    private Date fechaPartida;
    private boolean aceptacion;
    private int cantidadPersonas;
    private int status;
    private DTOPropiedad propiedad; 
    private DTOArrendatario arrendatario; 

    public Date getFechaLlegada() {
        return fechaLlegada;
    }

    public Date getFechaPartida() {
        return fechaPartida;
    }

    public boolean isAceptacion() {
        return aceptacion;
    }

    public int getCantidadPersonas() {
        return cantidadPersonas;
    }

    public int getStatus() {
        return status;
    }

    public DTOPropiedad getPropiedad() {
        return propiedad;
    }

    public DTOArrendatario getArrendatario() {
        return arrendatario;
    }
}