package com.arrienda.proyecto.controladores;

import java.util.*;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.arrienda.proyecto.modelos.Calificacion;
import com.arrienda.proyecto.servicios.ServicioCalificacion;

@RestController
@RequestMapping("/calificacion")
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

    @PostMapping("/crearCalificacion")
    public Calificacion crearCalificacion(@RequestBody Calificacion calificacion) {
        return servicioCalificacion.guardarCalificacion(calificacion);
    }

    @PutMapping("/actualizarCalificacion/{id}")
    public Calificacion actualizarCalificacion(@PathVariable Long id, @RequestBody Calificacion calificacion) {
        return servicioCalificacion.actualizarCalificacion(id, calificacion);
    }

    @DeleteMapping("/eliminarCalificaciones/{id}")
    public void eliminarCalificacion(@PathVariable Long id) {
        servicioCalificacion.eliminarCalificacion(id);
    }

}