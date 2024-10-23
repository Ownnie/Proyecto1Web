package com.arrienda.proyecto.dtos;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DTOArrendatario {
    private long id;
    private String usuario;
    private String nombre;
    private String correo;
    private float calificionPromedio;
    private int status;
    private List<DTOSolicitud> solicitudes;
    protected List<DTOCalificacion> calificaciones;
}
