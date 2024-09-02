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
    protected String usuario;
    protected String contrasena;
    private String nombre;
    protected int status;
    private String correo;
    private List<DTOSolicitud> solicitudes;
    protected List<DTOCalificacion> calificaciones;
}
