package com.arrienda.proyecto.servicios;

import java.util.*;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import com.arrienda.proyecto.modelos.Calificacion;
import com.arrienda.proyecto.repositorios.RepositorioCalificacion;

import jakarta.persistence.EntityNotFoundException;

import com.arrienda.proyecto.dtos.DTOCalificacion;

@Service
public class ServicioCalificacion {

    @Autowired
    private RepositorioCalificacion repositorioCalificacion;

    @Autowired
    private ModelMapper modelMapper;

    // Obtener todas las calificaciones activas
    public List<DTOCalificacion> traerCalificaciones() {
        return repositorioCalificacion.findAll().stream()
                .map(calificacion -> modelMapper.map(calificacion, DTOCalificacion.class))
                .collect(Collectors.toList());
    }

    // Obtener una calificación por su ID
    public DTOCalificacion traerCalificacionPorId(Long id) {
        Calificacion calificacion = repositorioCalificacion.findById(id)
                .orElse(null); // Return null if not found, or handle it as needed
        return modelMapper.map(calificacion, DTOCalificacion.class);
    }
    
    // Obtener las calificaciones de un arrendador, arrendatario o  propiedad
    public List<DTOCalificacion> getCalificaciones(Long id, int tipoId) {
        List<Calificacion> calificaciones = repositorioCalificacion.findByIdCalificadoAndIdTipo(id, tipoId);
        return calificaciones.stream()
                .map(calificacion -> modelMapper.map(calificacion, DTOCalificacion.class))
                .collect(Collectors.toList());
    }

    // Guardar una nueva calificación
    public DTOCalificacion guardarCalificacion(DTOCalificacion dtoCalificacion) {
        Calificacion calificacion = modelMapper.map(dtoCalificacion, Calificacion.class);
        Calificacion savedCalificacion = repositorioCalificacion.save(calificacion);
        return modelMapper.map(savedCalificacion, DTOCalificacion.class);
    }

    // Actualizar una calificación existente
    public DTOCalificacion actualizarCalificacion(Long id, DTOCalificacion dtoCalificacion) {
        Calificacion existingCalificacion = repositorioCalificacion.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Calificación no encontrada"));
    
        existingCalificacion.setCalificacion(dtoCalificacion.getCalificacion());
        existingCalificacion.setComentario(dtoCalificacion.getComentario());
        existingCalificacion.setIdTipo(dtoCalificacion.getIdTipo());
        existingCalificacion.setIdCalificado(dtoCalificacion.getIdCalificado());
        Calificacion updatedCalificacion = repositorioCalificacion.save(existingCalificacion);
        return modelMapper.map(updatedCalificacion, DTOCalificacion.class);
    }

    // Eliminar una calificación
    public void eliminarCalificacion(Long id) {
        Calificacion calificacion = repositorioCalificacion.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Calificación no encontrada"));

        repositorioCalificacion.deleteById(id);
    }
}
