package com.arrienda.proyecto.modelos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UsuarioTest {
    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsuario("usuarioTest");
        usuario.setContrasena("contrasenaTest");
        usuario.setNombre("nombreTest");
        usuario.setCorreo("correoTest");
        usuario.setStatus(0);
    }

    @Test
    public void testusuarioCreation() {
        assertNotNull(usuario);
        assertEquals(1L, usuario.getId());
        assertEquals("usuarioTest", usuario.getUsuario());
        assertEquals("contrasenaTest", usuario.getContrasena());
        assertEquals("nombreTest", usuario.getNombre());
        assertEquals("correoTest", usuario.getCorreo());
        assertEquals(0, usuario.getStatus());
    }

}
