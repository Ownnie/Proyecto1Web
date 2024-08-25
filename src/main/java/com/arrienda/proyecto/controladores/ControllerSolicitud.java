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
import com.arrienda.proyecto.modelos.*;
import com.arrienda.proyecto.servicios.*;

@RestController
@RequestMapping("/solicitud")
public class ControllerSolicitud {
    @Autowired
    private ServicioSolicitud servicioSolicitud;

    @GetMapping("/solicitudes")
    public List<Solicitud> getAllSolicitudes() {
        return servicioSolicitud.getAllSolicitudes();
    }

    @GetMapping("/solicitud/{id}")
    public Solicitud getSolicitud(@PathVariable Long id) {
        return servicioSolicitud.getSolicitud(id);
    }

    @GetMapping("/solicitudes/estado/{estado}")
    public List<Solicitud> getSolicitudesByEstado(@PathVariable int estado) {
        return servicioSolicitud.getSolicitudesByEstado(estado);
    }

    @GetMapping("/solicitudes/arrendatario/{arrendatarioId}")
    public List<Solicitud> getSolicitudesByArrendatarioId(@PathVariable Long arrendatarioId) {
        return servicioSolicitud.getSolicitudesByArrendatarioId(arrendatarioId);
    }

    @PostMapping("/crearSolicitud")
    public Solicitud crearSolicitud(@RequestBody Solicitud solicitud) {
        return servicioSolicitud.crearSolicitud(solicitud);
    }

    @PutMapping("/actualizarSolicitud/{id}")
    public Solicitud actualizarSolicitud(@PathVariable Long id, @RequestBody Solicitud solicitud) {
        return servicioSolicitud.actualizarSolicitud(id, solicitud);
    }

    @DeleteMapping("/eliminarSolicitud/{id}")
    public void eliminarSolicitud(@PathVariable Long id) {
        servicioSolicitud.eliminarSolicitud(id);
    }
}