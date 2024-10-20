package com.arrienda.proyecto.modelos;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "status = 0")
@SQLDelete(sql = "UPDATE arrendatario SET status = 1 WHERE id=?")
public class Arrendatario extends Usuario {

    @OneToMany
    private List<Solicitud> solicitudes;

    @OneToMany
    protected List<Calificacion> calificaciones;

}
