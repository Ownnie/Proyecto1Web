package com.arrienda.proyecto.modelos;

import java.sql.Date;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
@SQLDelete(sql = "UPDATE application SET status = 1 WHERE id=?")
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Date fechaLlegada;
    private Date fechaPartida;
    private boolean aceptacion;
    private int cantidadPersonas;
    protected int status;

    @ManyToOne
    private Propiedad propiedad;

    @ManyToOne
    private Arrendatario arrendatario;

    // Getters personalizados
    public Date getFechaLlegada() {
        return fechaLlegada;
    }

    public Date getFechaPartida() {
        return fechaPartida;
    }

    public boolean isAceptacion() {
        return aceptacion;
    }

    public int getCantidadPersonas() {
        return cantidadPersonas;
    }

    public int getStatus() {
        return status;
    }

    public Propiedad getPropiedad() {
        return propiedad;
    }

    public Arrendatario getArrendatario() {
        return arrendatario;
    }

    // Setters personalizados (si no estás usando Lombok para generar setters)
    public void setFechaLlegada(Date fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }

    public void setFechaPartida(Date fechaPartida) {
        this.fechaPartida = fechaPartida;
    }

    public void setAceptacion(boolean aceptacion) {
        this.aceptacion = aceptacion;
    }

    public void setCantidadPersonas(int cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setPropiedad(Propiedad propiedad) {
        this.propiedad = propiedad;
    }

    public void setArrendatario(Arrendatario arrendatario) {
        this.arrendatario = arrendatario;
    }

}
