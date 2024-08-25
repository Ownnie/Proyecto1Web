package com.arrienda.proyecto.controladores;

import java.util.*;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.arrienda.proyecto.modelos.Calificacion;
import com.arrienda.proyecto.servicios.ServicioCalificacion;

@RestController
@RequestMapping("/api/calificacion")
public class ControllerCalificacion {

    @Autowired
    private ServicioCalificacion servicioCalificacion;

    @GetMapping("/calificaciones")
    public List<Calificacion> getAllCalificaciones() {
        return servicioCalificacion.traerCalificaciones();
    }

    @GetMapping("/calificaciones/{id}")
    public Calificacion getCalificacionById(@PathVariable Long id) {
        return servicioCalificacion.traerCalificacionPorId(id);
    }

    @PostMapping("/calificaciones")
    public Calificacion crearCalificacion(@RequestBody Calificacion calificacion) {
        return servicioCalificacion.guardarCalificacion(calificacion);
    }

    @PutMapping("/calificaciones/{id}")
    public Calificacion actualizarCalificacion(@PathVariable Long id, @RequestBody Calificacion calificacion) {
        return servicioCalificacion.actualizarCalificacion(id, calificacion);
    }

    @DeleteMapping("/calificaciones/{id}")
    public void eliminarCalificacion(@PathVariable Long id) {
        servicioCalificacion.eliminarCalificacion(id);
    }

}