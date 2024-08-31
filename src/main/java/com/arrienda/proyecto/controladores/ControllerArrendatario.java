package com.arrienda.proyecto.controladores;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.arrienda.proyecto.dtos.*;
import com.arrienda.proyecto.servicios.*;

@RestController
@RequestMapping("/arrendatario")
public class ControllerArrendatario {

    @Autowired
    private ServicioArrendatario servicioArrendatario;

    @GetMapping("/arrendatarios")
    public List<DTOArrendatario> getAllArrendatarios() {
        return servicioArrendatario.getAllArrendatarios();
    }

    @GetMapping("/arrendatario/{id}")
    public DTOArrendatario getArrendatario(@PathVariable Long id) {
        return servicioArrendatario.getArrendatario(id);
    }

    @PostMapping("/crearArrendatario")
    public DTOArrendatario crearArrendatario(@RequestBody DTOArrendatario arrendatario) {
        return servicioArrendatario.createArrendatario(arrendatario);
    }

    @PutMapping("/actualizarArrendatario/{id}")
    public DTOArrendatario actualizarArrendador(@PathVariable Long id, @RequestBody DTOArrendatario arrendatario) {
        return servicioArrendatario.updateArrendatario(id, arrendatario);
    }

    @DeleteMapping("/eliminarArrendador/{id}")
    public void eliminarArrendatario(@PathVariable Long id) {
        servicioArrendatario.eliminarArrendatario(id);

    }

}