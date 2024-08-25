package com.arrienda.proyecto.servicios;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.arrienda.proyecto.modelos.Calificacion;
import com.arrienda.proyecto.repositorios.RepositorioCalificacion;

@Service
public class ServicioCalificacion {

    @Autowired
    private RepositorioCalificacion repositorioCalificacion;

    // Obtener todas las calificaciones activas
    public List<Calificacion> traerCalificaciones() {
        return repositorioCalificacion.findAll();
    }

    // Obtener una calificación por su ID
    public Calificacion traerCalificacionPorId(Long id) {
        Optional<Calificacion> calificacion = repositorioCalificacion.findById(id);
        return calificacion.orElse(null); // Return null if not found, or handle it as needed
    }

    // Guardar una nueva calificación
    public Calificacion guardarCalificacion(Calificacion calificacion) {
        return repositorioCalificacion.save(calificacion);
    }

    // Actualizar una calificación existente
    public Calificacion actualizarCalificacion(Long id, Calificacion nuevaCalificacion) {
        return repositorioCalificacion.findById(id)
                .map(calificacion -> {
                    calificacion.setCalificacion(nuevaCalificacion.getCalificacion());
                    calificacion.setComentario(nuevaCalificacion.getComentario());
                    calificacion.setIdTipo(nuevaCalificacion.getIdTipo());
                    calificacion.setIdCalificado(nuevaCalificacion.getIdCalificado());
                    return repositorioCalificacion.save(calificacion);
                })
                .orElseGet(() -> {
                    nuevaCalificacion.setId(id);
                    return repositorioCalificacion.save(nuevaCalificacion);
                });
    }

    // Eliminar una calificación (eliminación lógica)
    public void eliminarCalificacion(Long id) {
        repositorioCalificacion.deleteById(id);
    }
}
