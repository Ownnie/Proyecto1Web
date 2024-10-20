package com.arrienda.proyecto.modelos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ArrendadorTest {

    private Arrendador arrendador;

    @BeforeEach
    public void setUp() {
        arrendador = new Arrendador();
        arrendador.setId(1L);
        arrendador.setUsuario("usuarioTest");
        arrendador.setContrasena("contrasenaTest");
        arrendador.setNombre("nombreTest");
        arrendador.setCorreo("correoTest");
        arrendador.setStatus(0);
        arrendador.setPropiedades(new ArrayList<>());
        arrendador.setCalificaciones(new ArrayList<>());
        arrendador.setCalificionPromedio(0.0f);
    }

    @Test
    public void testArrendadorCreation() {
        assertNotNull(arrendador);
        assertEquals(1L, arrendador.getId());
        assertEquals("usuarioTest", arrendador.getUsuario());
        assertEquals("contrasenaTest", arrendador.getContrasena());
        assertEquals("nombreTest", arrendador.getNombre());
        assertEquals("correoTest", arrendador.getCorreo());
        assertEquals(0, arrendador.getStatus());
        assertNotNull(arrendador.getPropiedades());
        assertNotNull(arrendador.getCalificaciones());
        assertEquals(0.0f,arrendador.getCalificionPromedio());
    }

    @Test
    public void testSetAndGetPropiedades() {
        List<Propiedad> propiedades = new ArrayList<>();
        arrendador.setPropiedades(propiedades);
        assertEquals(propiedades, arrendador.getPropiedades());
    }

    @Test
    public void testSetAndGetCalificaciones() {
        List<Calificacion> calificaciones = new ArrayList<>();
        arrendador.setCalificaciones(calificaciones);
        assertEquals(calificaciones, arrendador.getCalificaciones());
    }
}
