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
public class DTOArrendatarioContrasena {
    private long id;
    private String usuario;
    private String nombre;
    private String contrasena;
    private String correo;
    private float calificacionPromedio;
    private int status;
    private List<DTOSolicitud> solicitudes;
    protected List<DTOCalificacion> calificaciones;
}