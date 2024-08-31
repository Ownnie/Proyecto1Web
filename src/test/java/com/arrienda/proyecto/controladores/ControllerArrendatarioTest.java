package com.arrienda.proyecto.controladores;

import jakarta.persistence.EntityNotFoundException;
import com.arrienda.proyecto.dtos.*;
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
@WebMvcTest(ControllerArrendatario.class)
public class ControllerArrendatarioTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ControllerArrendatario controllerArrendatario;

    @Test
    public void testGetAllArrendatarios() throws Exception {
        DTOArrendatario arrendatario1 = new DTOArrendatario();
        arrendatario1.setId(1L);
        arrendatario1.setNombre("Juan");
        arrendatario1.setCorreo("juan@example.com");

        DTOArrendatario arrendatario2 = new DTOArrendatario();
        arrendatario2.setId(2L);
        arrendatario2.setNombre("Maria");
        arrendatario2.setCorreo("maria@example.com");

        List<DTOArrendatario> arrendatario = Arrays.asList(arrendatario1, arrendatario2);

        when(controllerArrendatario.getAllArrendatarios()).thenReturn(arrendatario);

        mockMvc.perform(MockMvcRequestBuilders.get("/arrendatario/arrendatarios")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())

                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(
                        "[{\"id\":1,\"nombre\":\"Juan\",\"correo\":\"juan@example.com\"},{\"id\":2,\"nombre\":\"Maria\",\"correo\":\"maria@example.com\"}]"));
    }

    @Test
    public void testGetArrendatario() throws Exception {
        Long id = 1L;
        DTOArrendatario arrendatario = new DTOArrendatario();
        arrendatario.setId(id);

        when(controllerArrendatario.getArrendatario(id)).thenReturn(arrendatario);

        mockMvc.perform(MockMvcRequestBuilders.get("/arrendatario/arrendatario/{id}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())

                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testCrearArrendatario() throws Exception {
        String arrendatarioJson = "{\"id\":2,\"nombre\":\"Juan\",\"correo\":\"juan@example.com\"}";
        DTOArrendatario arrendatario = new DTOArrendatario();
        arrendatario.setId(2L);
        arrendatario.setNombre("Juan");
        arrendatario.setCorreo("juan@example.com");

        when(controllerArrendatario.crearArrendatario(any(DTOArrendatario.class))).thenReturn(arrendatario);

        mockMvc.perform(MockMvcRequestBuilders.post("/arrendatario/crearArrendatario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(arrendatarioJson))
                .andExpect(status().isOk())

                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(arrendatarioJson));

        verify(controllerArrendatario, times(1)).crearArrendatario(any(DTOArrendatario.class));
    }

    @Test
    public void testActualizarArrendatario() throws Exception {
        Long id = 1L;
        String arrendatarioJson = "{\"id\":1,\"nombre\":\"Juan Actualizado\",\"correo\":\"juan.actualizado@example.com\"}";
        DTOArrendatario arrendatario = new DTOArrendatario();
        arrendatario.setId(id);
        arrendatario.setNombre("Juan Actualizado");
        arrendatario.setCorreo("juan.actualizado@example.com");

        when(controllerArrendatario.actualizarArrendatario(eq(id), any(DTOArrendatario.class)))
                .thenReturn(arrendatario);

        mockMvc.perform(MockMvcRequestBuilders.put("/arrendatario/actualizarArrendatario/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(arrendatarioJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(arrendatarioJson));

        verify(controllerArrendatario, times(1)).actualizarArrendatario(eq(id), any(DTOArrendatario.class));
    }

    @Test
    public void testEliminarArrendatario() throws Exception {
        Long id = 1L;
        when(controllerArrendatario.eliminarArrendatario(id))
                .thenReturn(ResponseEntity.ok("Arrendatario eliminado con éxito."));

        mockMvc.perform(MockMvcRequestBuilders.delete("/arrendatario/eliminarArrendatario/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().string("Arrendatario eliminado con éxito."));

        verify(controllerArrendatario, times(1)).eliminarArrendatario(id);
    }

    @Test
    public void testEliminarArrendatarioNotFound() throws Exception {
        Long id = 1L;
        when(controllerArrendatario.eliminarArrendatario(id))
                .thenThrow(new EntityNotFoundException("Arrendatario no encontrado"));

        mockMvc.perform(MockMvcRequestBuilders.delete("/arrendatario/eliminarArrendatario/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Entidad no encontrada."));
    }

    @Test
    public void testEliminarArrendatarioError() throws Exception {
        Long id = 1L;
        when(controllerArrendatario.eliminarArrendatario(id))
                .thenThrow(new RuntimeException("Error interno del servidor"));

        mockMvc.perform(MockMvcRequestBuilders.delete("/arrendatario/eliminarArrendatario/{id}", id))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error interno del servidor."));
    }

}
