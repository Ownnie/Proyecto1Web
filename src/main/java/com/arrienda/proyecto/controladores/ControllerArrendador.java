package com.arrienda.proyecto.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.arrienda.proyecto.modelos.Arrendador;
import com.arrienda.proyecto.servicios.ServicioArrendador;

@RestController
@RequestMapping("/api/arrendador")
public class ControllerArrendador {

    @Autowired
    private ServicioArrendador servicioArrendador;

    @GetMapping("/arrendadores")
    public List<Arrendador> getAllArrendadores() {
        return servicioArrendador.traerArrendadores();
    }

    @GetMapping("/arrendador/{id}")
    public Arrendador getArrendador(@PathVariable Long id) {
        return servicioArrendador.traerArrendador(id);
    }

    @PostMapping("/crearArrendador")
    public Arrendador crearArrendador(@RequestBody Arrendador arrendador) {
        return servicioArrendador.crearArrendador(arrendador);
    }

    @PutMapping("/actualizarArrendador/{id}")
    public Arrendador actualizarArrendador(@PathVariable Long id, @RequestBody Arrendador arrendador) {
        return servicioArrendador.actualizarArrendador(id, arrendador);
    }

    @DeleteMapping("/eliminarArrendador/{id}")
    public void eliminarArrendador(@PathVariable Long id) {
        servicioArrendador.eliminarArrendador(id);
    }

}