package com.arrienda.proyecto.modelos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ArrendatarioTest {

    private Arrendatario arrendatario;

    @BeforeEach
    public void setUp() {
        arrendatario = new Arrendatario();
        arrendatario.setId(1L);
        arrendatario.setNombre("nombreTest");
        arrendatario.setUsuario("usuarioTest");
        arrendatario.setContrasena("contrasenaTest");
        arrendatario.setCorreo("correoTest");
        arrendatario.setStatus(0);
        arrendatario.setSolicitudes(new ArrayList<>());
        arrendatario.setCalificaciones(new ArrayList<>());
    }

    @Test
    public void testArrendatarioCreation() {
        assertNotNull(arrendatario);
        assertEquals(1L, arrendatario.getId());
        assertEquals("nombreTest", arrendatario.getNombre());
        assertEquals("usuarioTest", arrendatario.getUsuario());
        assertEquals("contrasenaTest", arrendatario.getContrasena());
        assertEquals("correoTest", arrendatario.getCorreo());
        assertEquals(0, arrendatario.getStatus());
        assertNotNull(arrendatario.getSolicitudes());
        assertNotNull(arrendatario.getCalificaciones());
    }

    @Test
    public void testSetAndGetSolicitudes() {
        List<Solicitud> solicitudes = new ArrayList<>();
        arrendatario.setSolicitudes(solicitudes);
        assertEquals(solicitudes, arrendatario.getSolicitudes());
    }

    @Test
    public void testSetAndGetCalificaciones() {
        List<Calificacion> calificaciones = new ArrayList<>();
        arrendatario.setCalificaciones(calificaciones);
        assertEquals(calificaciones, arrendatario.getCalificaciones());
    }
}