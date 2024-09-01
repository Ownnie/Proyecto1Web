package com.arrienda.proyecto.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DTOCalificacion {
    private long id;
    private float calificacion;
    private String comentario;
    private int status;
    private int idTipo;
    private long idCalificado;
}
