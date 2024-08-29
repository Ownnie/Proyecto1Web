package com.arrienda.proyecto.dtos;

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

    protected String usuario;
    protected String contrasena;
    private String nombre;
    protected int status;
}
