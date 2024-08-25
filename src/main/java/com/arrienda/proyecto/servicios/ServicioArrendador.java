package com.arrienda.proyecto.servicios;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
        Optional<Arrendador> optionalArrendador = repositorioArrendador.findById(id);
        return optionalArrendador.orElseThrow(() -> new RuntimeException("Arrendador not found"));
    }


    public List<Calificacion> getCalificaciones(Long id) {
        Optional<Arrendador> existingArrendador = repositorioArrendador.findById(id);
        if (existingArrendador.isPresent()) {
            return existingArrendador.get().getCalificaciones();
        } else {
            return Collections.emptyList(); 
        }
    }

    public List<Propiedad> getPropiedades(Long id) {
        Optional<Arrendador> existingArrendador = repositorioArrendador.findById(id);
        if (existingArrendador.isPresent()) {
            return existingArrendador.get().getPropiedades();
        } else {
            throw new RuntimeException("Arrendador not found");
        }
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