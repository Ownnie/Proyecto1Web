package com.arrienda.proyecto.controladores;

import jakarta.persistence.EntityNotFoundException;
import com.arrienda.proyecto.dtos.*;
import com.arrienda.proyecto.servicios.ServicioArrendatario;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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
        private ServicioArrendatario servicioArrendatario;

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

                when(servicioArrendatario.getAllArrendatarios()).thenReturn(arrendatario);

                mockMvc.perform(MockMvcRequestBuilders.get("/api/arrendatario/arrendatarios")
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

                when(servicioArrendatario.getArrendatario(id)).thenReturn(arrendatario);

                mockMvc.perform(MockMvcRequestBuilders.get("/api/arrendatario/arrendatario/{id}", id)
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

                when(servicioArrendatario.createArrendatario(any(DTOArrendatario.class))).thenReturn(arrendatario);

                mockMvc.perform(MockMvcRequestBuilders.post("/api/arrendatario/crearArrendatario")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(arrendatarioJson))
                                .andExpect(status().isOk())

                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(content().json(arrendatarioJson));

                verify(servicioArrendatario, times(1)).createArrendatario(any(DTOArrendatario.class));
        }

        @Test
        public void testActualizarArrendatario() throws Exception {
                Long id = 1L;
                String arrendatarioJson = "{\"id\":1,\"nombre\":\"Juan Actualizado\",\"correo\":\"juan.actualizado@example.com\"}";
                DTOArrendatario arrendatario = new DTOArrendatario();
                arrendatario.setId(id);
                arrendatario.setNombre("Juan Actualizado");
                arrendatario.setCorreo("juan.actualizado@example.com");

                when(servicioArrendatario.updateArrendatario(eq(id), any(DTOArrendatario.class)))
                                .thenReturn(arrendatario);

                mockMvc.perform(MockMvcRequestBuilders.put("/api/arrendatario/actualizarArrendatario/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(arrendatarioJson))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(content().json(arrendatarioJson));

                verify(servicioArrendatario, times(1)).updateArrendatario(eq(id), any(DTOArrendatario.class));
        }

        @Test
        public void testEliminarArrendatario() throws Exception {
                Long id = 1L;
                /*
                 * when(servicioArrendatario.eliminarArrendatario(id))
                 * .thenReturn(ResponseEntity.ok("Arrendatario eliminado con éxito."));
                 */

                mockMvc.perform(MockMvcRequestBuilders.delete("/api/arrendatario/eliminarArrendatario/{id}", id))
                                .andExpect(status().isOk())
                                .andExpect(content().string("Arrendatario eliminado con éxito."));

                verify(servicioArrendatario, times(1)).eliminarArrendatario(id);
        }

        @Test
        public void testEliminarArrendatarioNotFound() throws Exception {
                Long id = 1L;
                doThrow(new EntityNotFoundException("Entidad no encontrada")).when(servicioArrendatario)
                                .eliminarArrendatario(id);

                mockMvc.perform(MockMvcRequestBuilders.delete("/api/arrendatario/eliminarArrendatario/{id}", id))
                                .andExpect(status().isNotFound())
                                .andExpect(content().string("Arrendatario no encontrado."));
        }

        @Test
        public void testEliminarArrendatarioError() throws Exception {
                Long id = 1L;
                doThrow(new RuntimeException("Error interno del servidor")).when(servicioArrendatario)
                                .eliminarArrendatario(id);

                mockMvc.perform(MockMvcRequestBuilders.delete("/api/arrendatario/eliminarArrendatario/{id}", id))
                                .andExpect(status().isInternalServerError())
                                .andExpect(content().string("Error al eliminar el Arrendatario."));
        }

}
