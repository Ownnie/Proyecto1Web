package com.arrienda.proyecto.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DTOUsuario {
    private long id;
    private String usuario;
    private String nombre;
    private String correo;
    private String rol; 
    private float calificionPromedio;
    private int status;
}
