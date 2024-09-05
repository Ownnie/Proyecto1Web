package com.arrienda.proyecto.modelos;

import java.util.List;

import org.hibernate.annotations.Where;
import org.hibernate.annotations.SQLDelete;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "status = 0")
@SQLDelete(sql = "UPDATE arrendador SET status = 1 WHERE id=?")
public class Arrendador extends Usuario {

    @OneToMany
    private List<Propiedad> propiedades;

    @OneToMany
    protected List<Calificacion> calificaciones;

    public Arrendador(String usuario, String contrasena, String nombre, String correo) {
        super(usuario, contrasena, nombre, correo, Rol.ARRENDADOR, 0);
    }
}
