package com.arrienda.proyecto.servicios;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.arrienda.proyecto.repositorios.RepositorioArrendatario;
import com.arrienda.proyecto.modelos.Arrendatario;
import com.arrienda.proyecto.modelos.Calificacion;
import com.arrienda.proyecto.modelos.Solicitud;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ServicioArrendatario {
    private final RepositorioArrendatario repositorioArrendatario;

    @Autowired
    public ServicioArrendatario(RepositorioArrendatario repositorioArrendatario) {
        this.repositorioArrendatario = repositorioArrendatario;
    }

    public List<Arrendatario> getAllArrendatarios() {
        return repositorioArrendatario.findAll();
    }

    public Arrendatario getArrendatario(Long id){
        return repositorioArrendatario.findByArrendatarioId(id);
    }

    public Arrendatario createArrendatario (Arrendatario arrendatario){
        return repositorioArrendatario.save(arrendatario);
    }

    public Arrendatario updateArrendador (Long id, Arrendatario arrendatario){
        Arrendatario existingArrendador = repositorioArrendatario.findById(id).orElse(null);

        if (existingArrendador != null) {
            existingArrendador.setNombre(arrendatario.getNombre());
            existingArrendador.setUsuario(arrendatario.getUsuario());
            existingArrendador.setContrasena(arrendatario.getContrasena());
            return repositorioArrendatario.save(existingArrendador);
        } else {
            throw new EntityNotFoundException("Estudiante no encontrado"); //Arreglar esta 
        }

    }

    public List<Calificacion> getCalificaciones(Long id){
        Arrendatario existingArrendatario = repositorioArrendatario.findByArrendatarioId(id);
        return existingArrendatario.getCalificaciones();
    }

    public List<Solicitud> getSolicitudes(Long id) {
        Arrendatario existingArrendatario = repositorioArrendatario.findByArrendatarioId(id);
        return existingArrendatario.getSolicitudes();
    }


}