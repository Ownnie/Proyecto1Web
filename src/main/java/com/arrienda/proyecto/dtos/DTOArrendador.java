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
public class DTOArrendador {
    private long id;
    private String usuario;
    private String nombre;
    private String correo;
    private float calificacionPromedio;
    private int status;
    private List<DTOPropiedad> propiedades;
    private List<DTOCalificacion> calificaciones;
}
