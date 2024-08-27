package com.arrienda.proyecto.dtos;


import java.util.List;

import com.arrienda.proyecto.modelos.Calificacion;
import com.arrienda.proyecto.modelos.Propiedad;

import jakarta.persistence.OneToMany;
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

  //  protected String usuario;
//protected String contrasena;
    private String nombre;
    private int status;
}
