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
import com.arrienda.proyecto.servicios.ServicioPropiedad;

@RestController
@RequestMapping("/api/propiedades")
public class ControllerPropiedad {
    @Autowired
    private ServicioPropiedad servicioPropiedad;

    @GetMapping("/propiedades")
    public List<Propiedad> traerPropiedades() {
        return servicioPropiedad.traerPropiedades();
    }

    @GetMapping("/propiedades/{id}")
    public Propiedad obtenerPropiedad(@PathVariable Long id) {
        return servicioPropiedad.obtenerPropiedad(id);
    }

    @GetMapping("/camas/{camas}")
    public List<Propiedad> obtenerPropiedadesPorCamas(@PathVariable int camas) {
        return servicioPropiedad.obtenerPropiedadesPorCamas(camas);
    }

    @GetMapping("/disponibles")
    public List<Propiedad> obtenerPropiedadesDisponibles() {
        return servicioPropiedad.obtenerPropiedadesDisponibles();
    }

    @GetMapping("/cuartos/{cuartos}")
    public List<Propiedad> obtenerPropiedadesPorCuartos(@PathVariable int cuartos) {
        return servicioPropiedad.obtenerPropiedadesPorCuartos(cuartos);
    }

    @GetMapping("/area/{area}")
    public List<Propiedad> obtenerPropiedadesPorArea(@PathVariable float area) {
        return servicioPropiedad.obtenerPropiedadesPorArea(area);
    }

    @GetMapping("/capacidad/{capacidad}")
    public List<Propiedad> obtenerPropiedadesPorCapacidad(@PathVariable int capacidad) {
        return servicioPropiedad.obtenerPropiedadesPorCapacidad(capacidad);
    }

    @PostMapping("/crearPropiedad")
    public Propiedad crearPropiedad(@RequestBody Propiedad propiedad) {
        return servicioPropiedad.crearPropiedad(propiedad);
    }

    @PutMapping("/actualizarPropiedad/{id}")
    public Propiedad actualizarPropiedad(@PathVariable Long id, @RequestBody Propiedad propiedad) {
        return servicioPropiedad.actualizarPropiedad(id, propiedad);
    }

    @DeleteMapping("/eliminarPropiedad/{id}")
    public void eliminarPropiedad(@PathVariable Long id) {
        servicioPropiedad.eliminarPropiedad(id);
    }

}