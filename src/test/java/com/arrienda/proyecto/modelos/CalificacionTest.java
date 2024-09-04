package com.arrienda.proyecto.modelos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CalificacionTest {

    private Calificacion calificacion;

    @BeforeEach
    public void setUp() {
        calificacion = new Calificacion();
        calificacion.setId(1L);
        calificacion.setCalificacion(4.5f);
        calificacion.setComentario("Buen servicio");
        calificacion.setStatus(0);
        calificacion.setIdTipo(1);
        calificacion.setIdCalificado(2L);
    }

    @Test
    public void testCalificacionCreation() {
        assertNotNull(calificacion);
        assertEquals(1L, calificacion.getId());
        assertEquals(4.5f, calificacion.getCalificacion());
        assertEquals("Buen servicio", calificacion.getComentario());
        assertEquals(0, calificacion.getStatus());
        assertEquals(1, calificacion.getIdTipo());
        assertEquals(2L, calificacion.getIdCalificado());
    }

    @Test
    public void testSetAndGetCalificacion() {
        calificacion.setCalificacion(3.5f);
        assertEquals(3.5f, calificacion.getCalificacion());
    }

    @Test
    public void testSetAndGetComentario() {
        calificacion.setComentario("Servicio regular");
        assertEquals("Servicio regular", calificacion.getComentario());
    }

    @Test
    public void testSetAndGetStatus() {
        calificacion.setStatus(1);
        assertEquals(1, calificacion.getStatus());
    }

    @Test
    public void testSetAndGetIdTipo() {
        calificacion.setIdTipo(2);
        assertEquals(2, calificacion.getIdTipo());
    }

    @Test
    public void testSetAndGetIdCalificado() {
        calificacion.setIdCalificado(3L);
        assertEquals(3L, calificacion.getIdCalificado());
    }
}
