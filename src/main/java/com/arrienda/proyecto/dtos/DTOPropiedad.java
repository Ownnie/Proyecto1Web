package com.arrienda.proyecto.dtos;

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
    private float precioXnoche;
    protected int status;
    private long arrendadorId;
}
