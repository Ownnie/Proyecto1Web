package com.arrienda.proyecto.servicios;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.arrienda.proyecto.modelos.*;
import com.arrienda.proyecto.repositorios.*;

@Service
public class ServicioSolicitud {

     private final RepositorioSolicitud repositorioSolicitud;

    @Autowired
    public ServicioSolicitud(RepositorioSolicitud repositorioSolicitud) {
        this.repositorioSolicitud = repositorioSolicitud;
    }

    public List<Solicitud> getAllSolicitudes() {
        return repositorioSolicitud.findAll();
    }

    public Solicitud getSolicitud(Long id){
        return repositorioSolicitud.findBySolicitudId(id);
    }

    public List<Solicitud> getSolicitudesByEstado(int estado) {
        return repositorioSolicitud.findByStatus(estado);
    }

    public List<Solicitud> getSolicitudesByArrendatarioId(Long arrendatarioId) {
        return repositorioSolicitud.findByArrendatarioId(arrendatarioId);
    }

    public Solicitud crearSolicitud (Solicitud solicitud){
        return repositorioSolicitud.save(solicitud);
    }

    public Solicitud actualizarSolicitud(Long id, Solicitud solicitud) {
        Optional<Solicitud> solicitudExistente = repositorioSolicitud.findById(id);
        if (solicitudExistente.isPresent()) {
            Solicitud actualizada = solicitudExistente.get();
            actualizada.setFechaLlegada(solicitud.getFechaLlegada());
            actualizada.setFechaPartida(solicitud.getFechaPartida());
            actualizada.setAceptacion(solicitud.isAceptacion());
            actualizada.setCantidadPersonas(solicitud.getCantidadPersonas());
            actualizada.setStatus(solicitud.getStatus());
            actualizada.setPropiedad(solicitud.getPropiedad());
            actualizada.setArrendatario(solicitud.getArrendatario());
            return repositorioSolicitud.save(actualizada);
        } else {
            throw new RuntimeException("Solicitud no encontrada con id " + id);
        }
    }
    
    public void eliminarSolicitud(Long id) {
        repositorioSolicitud.deleteById(id);
    }

}