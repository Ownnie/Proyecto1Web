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

import com.arrienda.proyecto.dtos.DTOSolicitud;
import com.arrienda.proyecto.servicios.ServicioSolicitud;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/solicitud")
public class ControllerSolicitud {
    @Autowired
    private ServicioSolicitud servicioSolicitud;

    @GetMapping("/solicitudes")
    public List<DTOSolicitud> getAllSolicitudes() {
        return servicioSolicitud.getAllSolicitudes();
    }

    @GetMapping("/solicitud/{id}")
    public DTOSolicitud getSolicitud(@PathVariable Long id) {
        return servicioSolicitud.getSolicitud(id);
    }

    @GetMapping("/solicitudes/estado/{estado}")
    public List<DTOSolicitud> getSolicitudesByEstado(@PathVariable int estado) {
        return servicioSolicitud.getSolicitudesByEstado(estado);
    }

    @GetMapping("/solicitudes/arrendatario/{arrendatarioId}")
    public List<DTOSolicitud> getSolicitudesByArrendatarioId(@PathVariable Long arrendatarioId) {
        return servicioSolicitud.getSolicitudesByArrendatarioId(arrendatarioId);
    }

    @GetMapping("/solicitudes/propiedad/{propiedadId}")
    public List<DTOSolicitud> getSolicitudesByPropiedadId(@PathVariable Long propiedadId) {
        return servicioSolicitud.getSolicitudesByPropiedadId(propiedadId);
    }

    @PostMapping("/crearSolicitud")
    public DTOSolicitud crearSolicitud(@RequestBody DTOSolicitud solicitud) {
        return servicioSolicitud.crearSolicitud(solicitud);
    }

    @PutMapping("/actualizarSolicitud/{id}")
    public DTOSolicitud actualizarSolicitud(@PathVariable Long id, @RequestBody DTOSolicitud solicitud) {
        return servicioSolicitud.actualizarSolicitud(id, solicitud);
    }

    @PutMapping("/aceptarSolicitud/{id}")
    public DTOSolicitud aceptarSolicitud(@PathVariable Long id) {
        return servicioSolicitud.aceptarSolicitud(id);
    }

    @DeleteMapping("/eliminarSolicitud/{id}")
    public ResponseEntity<String> eliminarSolicitud(@PathVariable Long id) {
        try {
            servicioSolicitud.eliminarSolicitud(id);
            return ResponseEntity.ok("Solicitud eliminada con éxito.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Solicitud no encontrada.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la solicitud.");
        }
    }
}