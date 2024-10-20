package com.arrienda.proyecto.controladores;

import jakarta.persistence.EntityNotFoundException;
import com.arrienda.proyecto.dtos.DTOUsuario;
import com.arrienda.proyecto.servicios.ServicioUsuario;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ControllerUsuario.class)
public class ControllerUsuarioTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServicioUsuario servicioUsuario;

    @Test
    public void testGetAllUsuarios() throws Exception {
        DTOUsuario Usuario1 = new DTOUsuario();
        Usuario1.setId(1L);
        Usuario1.setNombre("Juan");
        Usuario1.setCorreo("juan@example.com");

        DTOUsuario Usuario2 = new DTOUsuario();
        Usuario2.setId(2L);
        Usuario2.setNombre("Maria");
        Usuario2.setCorreo("maria@example.com");

        List<DTOUsuario> Usuarios = Arrays.asList(Usuario1, Usuario2);

        when(servicioUsuario.traerUsuarios()).thenReturn(Usuarios);

        mockMvc.perform(MockMvcRequestBuilders.get("/usuario/usuarios")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(
                        "[{\"id\":1,\"nombre\":\"Juan\",\"correo\":\"juan@example.com\"},{\"id\":2,\"nombre\":\"Maria\",\"correo\":\"maria@example.com\"}]"));
    }

    @Test
    public void testGetUsuario() throws Exception {
        Long id = 1L;
        DTOUsuario usuario = new DTOUsuario();
        usuario.setId(id);
        when(servicioUsuario.traerUsuario(id)).thenReturn(usuario);

        mockMvc.perform(MockMvcRequestBuilders.get("/usuario/usuario/{id}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testCrearUsuario() throws Exception {
        String UsuarioJson = "{\"id\":2,\"nombre\":\"Juan\",\"correo\":\"juan@example.com\"}";
        DTOUsuario usuario = new DTOUsuario();
        usuario.setId(2L);
        usuario.setNombre("Juan");
        usuario.setCorreo("juan@example.com");

        when(servicioUsuario.crearUsuario(any(DTOUsuario.class))).thenReturn(usuario);

        mockMvc.perform(MockMvcRequestBuilders.post("/usuario/crearUsuario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(UsuarioJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(UsuarioJson));

        verify(servicioUsuario, times(1)).crearUsuario(any(DTOUsuario.class));
    }

    @Test
    public void testActualizarUsuario() throws Exception {
        Long id = 1L;
        String UsuarioJson = "{\"id\":1,\"nombre\":\"Juan Actualizado\",\"correo\":\"juan.actualizado@example.com\"}";
        DTOUsuario usuario = new DTOUsuario();
        usuario.setId(id);
        usuario.setNombre("Juan Actualizado");
        usuario.setCorreo("juan.actualizado@example.com");

        when(servicioUsuario.actualizarUsuario(eq(id), any(DTOUsuario.class))).thenReturn(usuario);

        mockMvc.perform(MockMvcRequestBuilders.put("/usuario/actualizarUsuario/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(UsuarioJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(UsuarioJson));

        verify(servicioUsuario, times(1)).actualizarUsuario(eq(id), any(DTOUsuario.class));
    }

    @Test
    public void testEliminarUsuario() throws Exception {
        Long id = 1L;
        /*
         * when(servicioUsuario.eliminarUsuario(id))
         * .thenReturn(ResponseEntity.ok("Usuario eliminado con éxito."));
         */

        mockMvc.perform(MockMvcRequestBuilders.delete("/usuario/eliminarUsuario/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuario eliminado con éxito."));

        verify(servicioUsuario, times(1)).eliminarUsuario(id);
    }

    @Test
    public void testEliminarUsuarioNotFound() throws Exception {
        Long id = 1L;
        doThrow(new EntityNotFoundException("Entidad no encontrada")).when(servicioUsuario)
                .eliminarUsuario(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/usuario/eliminarUsuario/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Usuario no encontrado."));
    }

    @Test
    public void testEliminarUsuarioError() throws Exception {
        Long id = 1L;
        doThrow(new RuntimeException("Error interno del servidor")).when(servicioUsuario)
                .eliminarUsuario(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/usuario/eliminarUsuario/{id}", id))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error al eliminar el Usuario."));
    }
}
