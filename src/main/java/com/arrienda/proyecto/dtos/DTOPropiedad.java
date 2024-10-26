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
public class DTOPropiedad {
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

    private List<DTOCalificacion> calificaciones;
    private List<DTOSolicitud> solicitudes;

    private Boolean lavanderia;
    private Boolean wifi;
    private Boolean mascotas;
    private Boolean gimnasios;
    private Boolean zonaJuegos;
    private Boolean alimentacion;
    private String propiedad;
    private List<String> imagenes;
}
