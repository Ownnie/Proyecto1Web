package com.arrienda.proyecto.modelos;

import java.sql.Date;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "status = 0")
@SQLDelete(sql = "UPDATE solicitud SET status = 1 WHERE id=?")
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Date fechaLlegada;
    private Date fechaPartida;
    private boolean aceptacion;
    private int cantidadPersonas;
    protected int status;
    private long propiedadId;
    private long arrendatarioId;

}
