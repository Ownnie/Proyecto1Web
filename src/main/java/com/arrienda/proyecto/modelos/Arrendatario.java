package com.arrienda.proyecto.modelos;

import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Arrendatario extends Usuario{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private ArrayList<Solicitud> solicitudes;
    
}
