package com.arrienda.proyecto.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/solicitud")
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

    @PostMapping("/crearSolicitud")
    public DTOSolicitud crearSolicitud(@RequestBody DTOSolicitud solicitud) {
        return servicioSolicitud.crearSolicitud(solicitud);
    }

    @PutMapping("/actualizarSolicitud/{id}")
    public DTOSolicitud actualizarSolicitud(@PathVariable Long id, @RequestBody DTOSolicitud solicitud) {
        return servicioSolicitud.actualizarSolicitud(id, solicitud);
    }

    @DeleteMapping("/eliminarSolicitud/{id}")
    public void eliminarSolicitud(@PathVariable Long id) {
        servicioSolicitud.eliminarSolicitud(id);
    }
}