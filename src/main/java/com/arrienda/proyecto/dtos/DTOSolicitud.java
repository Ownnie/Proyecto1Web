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
}