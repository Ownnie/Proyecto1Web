package com.arrienda.proyecto.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arrienda.proyecto.dtos.DTOCalificacion;
import com.arrienda.proyecto.servicios.ServicioCalificacion;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/calificacion")
public class ControllerCalificacion {

    @Autowired
    private ServicioCalificacion servicioCalificacion;

    @GetMapping("/calificaciones")
    public List<DTOCalificacion> getAllCalificaciones() {
        return servicioCalificacion.traerCalificaciones();
    }

    @GetMapping("/calificaciones/{id}")
    public DTOCalificacion getCalificacionById(@PathVariable Long id) {
        return servicioCalificacion.traerCalificacionPorId(id);
    }

    @GetMapping("/arrendador/{id}/calificaciones")
    public List<DTOCalificacion> getCalificacionesArrendador(@PathVariable Long id) {
        return servicioCalificacion.getCalificaciones(id, 0);
    }

    @GetMapping("/arrendatario/{id}/calificaciones")
    public List<DTOCalificacion> getCalificacionesArrendatario(@PathVariable Long id) {
        return servicioCalificacion.getCalificaciones(id, 1);
    }

    @GetMapping("/propiedad/{id}/calificaciones")
    public List<DTOCalificacion> getCalificacionesPropiedad(@PathVariable Long id) {
        return servicioCalificacion.getCalificaciones(id, 2);
    }

    @PostMapping("/crearCalificacion")
    public DTOCalificacion crearCalificacion(@RequestBody DTOCalificacion calificacion) {
        return servicioCalificacion.guardarCalificacion(calificacion);
    }

    @PutMapping("/actualizarCalificacion/{id}")
    public DTOCalificacion actualizarCalificacion(@PathVariable Long id, @RequestBody DTOCalificacion calificacion) {
        return servicioCalificacion.actualizarCalificacion(id, calificacion);
    }

    @DeleteMapping("/eliminarCalificaciones/{id}")
    public ResponseEntity<String> eliminarCalificacion(@PathVariable Long id) {
        try {
            servicioCalificacion.eliminarCalificacion(id);
            return ResponseEntity.ok("Calificación eliminada con éxito.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Calificación no encontrada.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la calificación.");
        }
    }

}