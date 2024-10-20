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
    private ServicioArrendador servicioArrendador;

    @Autowired
    private ServicioArrendatario servicioArrendatario;

    @Autowired
    private ServicioPropiedad servicioPropiedad;

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

        actualizarPromedio(savedCalificacion);

        return modelMapper.map(savedCalificacion, DTOCalificacion.class);
    }

    public void actualizarPromedio(Calificacion calificacion) {
        int tipo = calificacion.getIdTipo();
        long id = calificacion.getIdCalificado();

        List<Calificacion> calificaciones = repositorioCalificacion.findByIdCalificadoAndIdTipo(id, tipo);

        float calificacionPromedio = calcularPromedio(calificaciones); 

        switch (calificacion.getIdTipo()) {
            case 0:
                servicioArrendador.actualizarPromedioCalificacion(id, calificacionPromedio);
                break;
            case 1:
                servicioArrendatario.actualizarPromedioCalificacion(id, calificacionPromedio);
                break;
            case 2:
                servicioPropiedad.actualizarPromedioCalificacion(id, calificacionPromedio);
                break;
        }
    }

    public float calcularPromedio(List<Calificacion> calificaciones) {
        if (calificaciones == null || calificaciones.isEmpty()) {
            return 0.0f;
        }
        float sum = 0.0f;
        for (Calificacion calificacion : calificaciones) {
            sum += calificacion.getCalificacion();
        }
        return sum / calificaciones.size();
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

        actualizarPromedio(updatedCalificacion);

        return modelMapper.map(updatedCalificacion, DTOCalificacion.class);
    }

    public void eliminarCalificacion(Long id) {
        Calificacion calificacion = repositorioCalificacion.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Calificación no encontrada"));
        
        repositorioCalificacion.deleteById(id);
        actualizarPromedio(calificacion);
    }
}
