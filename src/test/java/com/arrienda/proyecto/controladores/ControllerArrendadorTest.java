package com.arrienda.proyecto.controladores;

import jakarta.persistence.EntityNotFoundException;
import com.arrienda.proyecto.dtos.DTOArrendador;
import com.arrienda.proyecto.dtos.DTOArrendadorContrasena;
import com.arrienda.proyecto.servicios.ServicioArrendador;

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
@WebMvcTest(ControllerArrendador.class)
public class ControllerArrendadorTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private ServicioArrendador servicioArrendador;

        @Test
        public void testGetAllArrendadores() throws Exception {
                DTOArrendador arrendador1 = new DTOArrendador();
                arrendador1.setId(1L);
                arrendador1.setNombre("Juan");
                arrendador1.setCorreo("juan@example.com");

                DTOArrendador arrendador2 = new DTOArrendador();
                arrendador2.setId(2L);
                arrendador2.setNombre("Maria");
                arrendador2.setCorreo("maria@example.com");

                List<DTOArrendador> arrendadores = Arrays.asList(arrendador1, arrendador2);

                when(servicioArrendador.traerArrendadores()).thenReturn(arrendadores);

                mockMvc.perform(MockMvcRequestBuilders.get("/api/arrendador/arrendadores")
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(content().json(
                                                "[{\"id\":1,\"nombre\":\"Juan\",\"correo\":\"juan@example.com\"},{\"id\":2,\"nombre\":\"Maria\",\"correo\":\"maria@example.com\"}]"));
        }

        @Test
        public void testGetArrendador() throws Exception {
                Long id = 1L;
                DTOArrendador arrendador = new DTOArrendador();
                arrendador.setId(id);
                when(servicioArrendador.traerArrendador(id)).thenReturn(arrendador);

                mockMvc.perform(MockMvcRequestBuilders.get("/api/arrendador/arrendador/{id}", id)
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        public void testCrearArrendador() throws Exception {
                String arrendadorJson = "{\"id\":2,\"nombre\":\"Juan\",\"correo\":\"juan@example.com\"}";
                DTOArrendador arrendador = new DTOArrendador();
                arrendador.setId(2L);
                arrendador.setNombre("Juan");
                arrendador.setCorreo("juan@example.com");

                when(servicioArrendador.crearArrendador(any(DTOArrendadorContrasena.class))).thenReturn(arrendador);

                mockMvc.perform(MockMvcRequestBuilders.post("/api/arrendador/crearArrendador")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(arrendadorJson))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(content().json(arrendadorJson));

                verify(servicioArrendador, times(1)).crearArrendador(any(DTOArrendadorContrasena.class));
        }

        @Test
        public void testActualizarArrendador() throws Exception {
                Long id = 1L;
                String arrendadorJson = "{\"id\":1,\"nombre\":\"Juan Actualizado\",\"correo\":\"juan.actualizado@example.com\"}";
                DTOArrendador arrendador = new DTOArrendador();
                arrendador.setId(id);
                arrendador.setNombre("Juan Actualizado");
                arrendador.setCorreo("juan.actualizado@example.com");

                when(servicioArrendador.actualizarArrendador(eq(id), any(DTOArrendadorContrasena.class)))
                                .thenReturn(arrendador);

                mockMvc.perform(MockMvcRequestBuilders.put("/api/arrendador/actualizarArrendador/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(arrendadorJson))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(content().json(arrendadorJson));

                verify(servicioArrendador, times(1)).actualizarArrendador(eq(id), any(DTOArrendadorContrasena.class));
        }

        @Test
        public void testEliminarArrendador() throws Exception {
                Long id = 1L;
                /*
                 * when(servicioArrendador.eliminarArrendador(id))
                 * .thenReturn(ResponseEntity.ok("Arrendador eliminado con éxito."));
                 */

                mockMvc.perform(MockMvcRequestBuilders.delete("/api/arrendador/eliminarArrendador/{id}", id))
                                .andExpect(status().isOk())
                                .andExpect(content().string("Arrendador eliminado con éxito."));

                verify(servicioArrendador, times(1)).eliminarArrendador(id);
        }

        @Test
        public void testEliminarArrendadorNotFound() throws Exception {
                Long id = 1L;
                doThrow(new EntityNotFoundException("Entidad no encontrada")).when(servicioArrendador)
                                .eliminarArrendador(id);

                mockMvc.perform(MockMvcRequestBuilders.delete("/api/arrendador/eliminarArrendador/{id}", id))
                                .andExpect(status().isNotFound())
                                .andExpect(content().string("Arrendador no encontrado."));
        }

        @Test
        public void testEliminarArrendadorError() throws Exception {
                Long id = 1L;
                doThrow(new RuntimeException("Error interno del servidor")).when(servicioArrendador)
                                .eliminarArrendador(id);

                mockMvc.perform(MockMvcRequestBuilders.delete("/api/arrendador/eliminarArrendador/{id}", id))
                                .andExpect(status().isInternalServerError())
                                .andExpect(content().string("Error al eliminar el arrendador."));
        }
}