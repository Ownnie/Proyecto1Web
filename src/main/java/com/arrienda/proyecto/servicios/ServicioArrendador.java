package com.arrienda.proyecto.servicios;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.arrienda.proyecto.modelos.*;
import com.arrienda.proyecto.repositorios.*;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ServicioArrendador {

    private final RepositorioArrendador repositorioArrendador;

    @Autowired
    public ServicioArrendador(RepositorioArrendador repositorioArrendador) {
        this.repositorioArrendador = repositorioArrendador;
    }

    public List<Arrendador> traerArrendadores() {
        return repositorioArrendador.findAll();
    }

    public Arrendador traerArrendador(Long id) {
        return repositorioArrendador.findByArrendadorId(id);
    }


    public List<Calificacion> getCalificaciones(Long id){
        Arrendador existingArrendador = repositorioArrendador.findByArrendadorId(id);
        return existingArrendador.getCalificaciones();
    }

    public List<Propiedad> getPropiedades(Long id) {
        Arrendador existingArrendador = repositorioArrendador.findByArrendadorId(id);
        return existingArrendador.getPropiedades();
    }

    public Arrendador crearArrendador (Arrendador arrendador){
        return repositorioArrendador.save(arrendador);
    }

    public Arrendador actualizarArrendador(Long id, Arrendador arrendador) {
        Arrendador existingArrendador = repositorioArrendador.findById(id).orElse(null);

        if (existingArrendador != null) {
            existingArrendador.setNombre(arrendador.getNombre());
            existingArrendador.setUsuario(arrendador.getUsuario());
            existingArrendador.setContrasena(arrendador.getContrasena());
            existingArrendador.setStatus(arrendador.getStatus());
            return repositorioArrendador.save(existingArrendador);
        } else {
            throw new EntityNotFoundException("Arrendador no encontrado");
        }
    }

    public void eliminarArrendador(Long id) {
        Arrendador existingArrendador = repositorioArrendador.findById(id).orElse(null);
        if (existingArrendador != null) {
            repositorioArrendador.delete(existingArrendador);
        } else {
            throw new EntityNotFoundException("Arrendador no encontrado");
        }
    }

}