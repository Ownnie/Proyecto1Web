package com.arrienda.proyecto.controladores;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.arrienda.proyecto.modelos.Arrendatario;
import com.arrienda.proyecto.modelos.Calificacion;
import com.arrienda.proyecto.modelos.Solicitud;
import com.arrienda.proyecto.servicios.ServicioArrendatario;


@RestController
@RequestMapping("/api/arrendatario")
public class ControllerArrendatario {

    @Autowired
    private ServicioArrendatario servicioArrendatario;

    @GetMapping("/arrendatario")
    public List<Arrendatario> getAllArrendatarios() {
        return servicioArrendatario.getAllArrendatarios();
    }

    @GetMapping("/arrendatario/{id}")
    public Arrendatario getArrendatario(@PathVariable Long id){
        return servicioArrendatario.getArrendatario(id);
    }

    @PostMapping("/crearArrendatario")
    public Arrendatario crearArrendatario(@RequestBody Arrendatario arrendatario) {
        return servicioArrendatario.createArrendatario(arrendatario);
    }

    @PutMapping("/actualizarArrendatario/{id}")
    public Arrendatario actualizarArrendador(@PathVariable Long id, @RequestBody Arrendatario arrendatario) {
        return sevicioArrendatario.updateArrendatario(id, arrendatario);
    }
        
    @GetMapping("/arrendatario/{id}/calificaciones")
    public List<Calificacion> getCalificaciones(@PathVariable Long id) {
        return servicioArrendatario.getCalificaciones(id);
    }

    @GetMapping("/arrendatario/{id}/solicitudes")
    public List<Solicitud> getSolicitudes(@PathVariable Long id) {
        return servicioArrendatario.getSolicitudes(id);
    }
        
        
    

}