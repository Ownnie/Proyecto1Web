package com.arrienda.proyecto.controladores;

import jakarta.persistence.EntityNotFoundException;

import com.arrienda.proyecto.dtos.DTOCalificacion;
import com.arrienda.proyecto.dtos.DTOPropiedad;
import com.arrienda.proyecto.dtos.DTOSolicitud;
import com.arrienda.proyecto.servicios.ServicioPropiedad;

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
@WebMvcTest(ControllerPropiedad.class)
public class ControllerPropiedadTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private ServicioPropiedad servicioPropiedad;

        @Test
        public void testTraerPropiedades() throws Exception {
                DTOPropiedad propiedad1 = new DTOPropiedad();
                propiedad1.setId(1L);
                propiedad1.setNombre("Casa de playa");
                propiedad1.setUbicacion("Ubicación 1");
                propiedad1.setParqueadero(true);
                propiedad1.setPiscina(true);
                propiedad1.setCuartos(3);
                propiedad1.setCamas(5);
                propiedad1.setArea(200.5f);
                propiedad1.setCapacidad(10);
                propiedad1.setDisponible(true);
                propiedad1.setPrecioXnoche(300L);
                propiedad1.setStatus(1);
                propiedad1.setArrendadorId(1L);

                DTOPropiedad propiedad2 = new DTOPropiedad();
                propiedad2.setId(2L);
                propiedad2.setNombre("Apartamento en la ciudad");
                propiedad2.setUbicacion("Ubicación 2");
                propiedad2.setParqueadero(false);
                propiedad2.setPiscina(false);
                propiedad2.setCuartos(2);
                propiedad2.setCamas(3);
                propiedad2.setArea(80.0f);
                propiedad2.setCapacidad(5);
                propiedad2.setDisponible(true);
                propiedad2.setPrecioXnoche(150L);
                propiedad2.setStatus(1);
                propiedad2.setArrendadorId(2L);

                List<DTOPropiedad> propiedades = Arrays.asList(propiedad1, propiedad2);

                when(servicioPropiedad.traerPropiedades()).thenReturn(propiedades);

                mockMvc.perform(MockMvcRequestBuilders.get("/api/propiedad/propiedades")
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(content().json(
                                                "[{\"id\":1,\"nombre\":\"Casa de playa\",\"ubicacion\":\"Ubicación 1\",\"parqueadero\":true,\"piscina\":true,\"cuartos\":3,\"camas\":5,\"area\":200.5,\"capacidad\":10,\"disponible\":true,\"precioXnoche\":300.0,\"status\":1,\"arrendadorId\":1},"
                                                                +
                                                                "{\"id\":2,\"nombre\":\"Apartamento en la ciudad\",\"ubicacion\":\"Ubicación 2\",\"parqueadero\":false,\"piscina\":false,\"cuartos\":2,\"camas\":3,\"area\":80.0,\"capacidad\":5,\"disponible\":true,\"precioXnoche\":150.0,\"status\":1,\"arrendadorId\":2}]"));
                verify(servicioPropiedad, times(1)).traerPropiedades();
        }

        @Test
        public void testObtenerPropiedad() throws Exception {
                DTOPropiedad propiedad = new DTOPropiedad();
                propiedad.setId(1L);
                propiedad.setNombre("Casa de playa");
                propiedad.setUbicacion("Ubicación 1");
                propiedad.setParqueadero(true);
                propiedad.setPiscina(true);
                propiedad.setCuartos(3);
                propiedad.setCamas(5);
                propiedad.setArea(200.5f);
                propiedad.setCapacidad(10);
                propiedad.setDisponible(true);
                propiedad.setPrecioXnoche(300L);
                propiedad.setStatus(1);
                propiedad.setArrendadorId(1L);

                when(servicioPropiedad.obtenerPropiedad(1L)).thenReturn(propiedad);

                mockMvc.perform(MockMvcRequestBuilders.get("/api/propiedad/propiedad/1")
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(content().json(
                                                "{\"id\":1,\"nombre\":\"Casa de playa\",\"ubicacion\":\"Ubicación 1\",\"parqueadero\":true,\"piscina\":true,\"cuartos\":3,\"camas\":5,\"area\":200.5,\"capacidad\":10,\"disponible\":true,\"precioXnoche\":300.0,\"status\":1,\"arrendadorId\":1}"));
                verify(servicioPropiedad, times(1)).obtenerPropiedad(1L);
        }

        @Test
        public void testObtenerPropiedadesPorCamas() throws Exception {
                DTOPropiedad propiedad = new DTOPropiedad();
                propiedad.setId(1L);
                propiedad.setNombre("Casa de playa");
                propiedad.setUbicacion("Ubicación 1");
                propiedad.setParqueadero(true);
                propiedad.setPiscina(true);
                propiedad.setCuartos(3);
                propiedad.setCamas(5);
                propiedad.setArea(200.5f);
                propiedad.setCapacidad(10);
                propiedad.setDisponible(true);
                propiedad.setPrecioXnoche(300L);
                propiedad.setStatus(1);
                propiedad.setArrendadorId(1L);

                List<DTOPropiedad> propiedades = Arrays.asList(propiedad);

                when(servicioPropiedad.obtenerPropiedadesPorCamas(5)).thenReturn(propiedades);

                mockMvc.perform(MockMvcRequestBuilders.get("/api/propiedad/camas/5")
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(content().json(
                                                "[{\"id\":1,\"nombre\":\"Casa de playa\",\"ubicacion\":\"Ubicación 1\",\"parqueadero\":true,\"piscina\":true,\"cuartos\":3,\"camas\":5,\"area\":200.5,\"capacidad\":10,\"disponible\":true,\"precioXnoche\":300.0,\"status\":1,\"arrendadorId\":1}]"));
                verify(servicioPropiedad, times(1)).obtenerPropiedadesPorCamas(5);
        }

        @Test
        public void testObtenerPropiedadesDisponibles() throws Exception {
                DTOPropiedad propiedad = new DTOPropiedad();
                propiedad.setId(1L);
                propiedad.setNombre("Casa de playa");
                propiedad.setUbicacion("Ubicación 1");
                propiedad.setParqueadero(true);
                propiedad.setPiscina(true);
                propiedad.setCuartos(3);
                propiedad.setCamas(5);
                propiedad.setArea(200.5f);
                propiedad.setCapacidad(10);
                propiedad.setDisponible(true);
                propiedad.setPrecioXnoche(300L);
                propiedad.setStatus(1);
                propiedad.setArrendadorId(1L);

                List<DTOPropiedad> propiedades = Arrays.asList(propiedad);

                when(servicioPropiedad.obtenerPropiedadesDisponibles()).thenReturn(propiedades);

                mockMvc.perform(MockMvcRequestBuilders.get("/api/propiedad/disponibles")
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(content().json(
                                                "[{\"id\":1,\"nombre\":\"Casa de playa\",\"ubicacion\":\"Ubicación 1\",\"parqueadero\":true,\"piscina\":true,\"cuartos\":3,\"camas\":5,\"area\":200.5,\"capacidad\":10,\"disponible\":true,\"precioXnoche\":300.0,\"status\":1,\"arrendadorId\":1}]"));
                verify(servicioPropiedad, times(1)).obtenerPropiedadesDisponibles();
        }

        @Test
        public void testObtenerPropiedadesPorCuartos() throws Exception {
                DTOPropiedad propiedad = new DTOPropiedad();
                propiedad.setId(1L);
                propiedad.setNombre("Casa de playa");
                propiedad.setUbicacion("Ubicación 1");
                propiedad.setParqueadero(true);
                propiedad.setPiscina(true);
                propiedad.setCuartos(3);
                propiedad.setCamas(5);
                propiedad.setArea(200.5f);
                propiedad.setCapacidad(10);
                propiedad.setDisponible(true);
                propiedad.setPrecioXnoche(300L);
                propiedad.setStatus(1);
                propiedad.setArrendadorId(1L);

                List<DTOPropiedad> propiedades = Arrays.asList(propiedad);

                when(servicioPropiedad.obtenerPropiedadesPorCuartos(3)).thenReturn(propiedades);

                mockMvc.perform(MockMvcRequestBuilders.get("/api/propiedad/cuartos/3")
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(content().json(
                                                "[{\"id\":1,\"nombre\":\"Casa de playa\",\"ubicacion\":\"Ubicación 1\",\"parqueadero\":true,\"piscina\":true,\"cuartos\":3,\"camas\":5,\"area\":200.5,\"capacidad\":10,\"disponible\":true,\"precioXnoche\":300.0,\"status\":1,\"arrendadorId\":1}]"));
                verify(servicioPropiedad, times(1)).obtenerPropiedadesPorCuartos(3);
        }

        @Test
        public void testObtenerPropiedadesPorArea() throws Exception {
                DTOPropiedad propiedad = new DTOPropiedad();
                propiedad.setId(1L);
                propiedad.setNombre("Casa de playa");
                propiedad.setUbicacion("Ubicación 1");
                propiedad.setParqueadero(true);
                propiedad.setPiscina(true);
                propiedad.setCuartos(3);
                propiedad.setCamas(5);
                propiedad.setArea(200.5f);
                propiedad.setCapacidad(10);
                propiedad.setDisponible(true);
                propiedad.setPrecioXnoche(300L);
                propiedad.setStatus(1);
                propiedad.setArrendadorId(1L);

                List<DTOPropiedad> propiedades = Arrays.asList(propiedad);

                when(servicioPropiedad.obtenerPropiedadesPorArea(200.5f)).thenReturn(propiedades);

                mockMvc.perform(MockMvcRequestBuilders.get("/api/propiedad/area/200.5")
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(content().json(
                                                "[{\"id\":1,\"nombre\":\"Casa de playa\",\"ubicacion\":\"Ubicación 1\",\"parqueadero\":true,\"piscina\":true,\"cuartos\":3,\"camas\":5,\"area\":200.5,\"capacidad\":10,\"disponible\":true,\"precioXnoche\":300.0,\"status\":1,\"arrendadorId\":1}]"));
                verify(servicioPropiedad, times(1)).obtenerPropiedadesPorArea(200.5f);
        }

        @Test
        public void testObtenerPropiedadesPorCapacidad() throws Exception {
                DTOPropiedad propiedad = new DTOPropiedad();
                propiedad.setId(1L);
                propiedad.setNombre("Casa de playa");
                propiedad.setUbicacion("Ubicación 1");
                propiedad.setParqueadero(true);
                propiedad.setPiscina(true);
                propiedad.setCuartos(3);
                propiedad.setCamas(5);
                propiedad.setArea(200.5f);
                propiedad.setCapacidad(10);
                propiedad.setDisponible(true);
                propiedad.setPrecioXnoche(300L);
                propiedad.setStatus(1);
                propiedad.setArrendadorId(1L);

                List<DTOPropiedad> propiedades = Arrays.asList(propiedad);

                when(servicioPropiedad.obtenerPropiedadesPorCapacidad(10)).thenReturn(propiedades);

                mockMvc.perform(MockMvcRequestBuilders.get("/api/propiedad/capacidad/10")
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(content().json(
                                                "[{\"id\":1,\"nombre\":\"Casa de playa\",\"ubicacion\":\"Ubicación 1\",\"parqueadero\":true,\"piscina\":true,\"cuartos\":3,\"camas\":5,\"area\":200.5,\"capacidad\":10,\"disponible\":true,\"precioXnoche\":300.0,\"status\":1,\"arrendadorId\":1}]"));
                verify(servicioPropiedad, times(1)).obtenerPropiedadesPorCapacidad(10);
        }

        @Test
        public void testGetPropiedadesPorArrendadorId() throws Exception {
                DTOPropiedad propiedad = new DTOPropiedad();
                propiedad.setId(1L);
                propiedad.setNombre("Casa de playa");
                propiedad.setUbicacion("Ubicación 1");
                propiedad.setParqueadero(true);
                propiedad.setPiscina(true);
                propiedad.setCuartos(3);
                propiedad.setCamas(5);
                propiedad.setArea(200.5f);
                propiedad.setCapacidad(10);
                propiedad.setDisponible(true);
                propiedad.setPrecioXnoche(300L);
                propiedad.setStatus(1);
                propiedad.setArrendadorId(1L);

                List<DTOPropiedad> propiedades = Arrays.asList(propiedad);

                when(servicioPropiedad.getPropiedades(1L)).thenReturn(propiedades);

                mockMvc.perform(MockMvcRequestBuilders.get("/api/propiedad/arrendador/1/propiedades")
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(content().json(
                                                "[{\"id\":1,\"nombre\":\"Casa de playa\",\"ubicacion\":\"Ubicación 1\",\"parqueadero\":true,\"piscina\":true,\"cuartos\":3,\"camas\":5,\"area\":200.5,\"capacidad\":10,\"disponible\":true,\"precioXnoche\":300.0,\"status\":1,\"arrendadorId\":1}]")); // Incluir
                                                                                                                                                                                                                                                                                                        // arrendadorId
                                                                                                                                                                                                                                                                                                        // en
                                                                                                                                                                                                                                                                                                        // la
                                                                                                                                                                                                                                                                                                        // verificación
                                                                                                                                                                                                                                                                                                        // del
                                                                                                                                                                                                                                                                                                        // JSON

                verify(servicioPropiedad, times(1)).getPropiedades(1L);
        }

        @Test
        public void testCrearPropiedad() throws Exception {
                List<DTOCalificacion> calificaciones = Collections.emptyList();
                List<DTOSolicitud> solicitudes = Collections.emptyList();
                DTOPropiedad propiedad = new DTOPropiedad();
                propiedad.setId(1L);
                propiedad.setNombre("Casa de playa");
                propiedad.setUbicacion("Ubicación 1");
                propiedad.setParqueadero(true);
                propiedad.setPiscina(true);
                propiedad.setCuartos(3);
                propiedad.setCamas(5);
                propiedad.setArea(200.5f);
                propiedad.setCapacidad(10);
                propiedad.setDisponible(true);
                propiedad.setPrecioXnoche(300L);
                propiedad.setStatus(1);
                propiedad.setArrendadorId(1L);
                propiedad.setCalificaciones(calificaciones);
                propiedad.setSolicitudes(solicitudes);

                when(servicioPropiedad.crearPropiedad(any(DTOPropiedad.class))).thenReturn(propiedad);

                mockMvc.perform(MockMvcRequestBuilders.post("/api/propiedad/crearPropiedad")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"nombre\":\"Casa de playa\",\"camas\":3}")
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(content().json(
                                                "{\"id\":1,\"nombre\":\"Casa de playa\",\"ubicacion\":\"Ubicación 1\",\"parqueadero\":true,\"piscina\":true,\"cuartos\":3,\"camas\":5,\"area\":200.5,\"capacidad\":10,\"disponible\":true,\"precioXnoche\":300.0,\"status\":1,\"arrendadorId\":1,\"calificaciones\":[],\"solicitudes\":[]}")); // Incluir
                // arrendadorId
                // en
                // la
                // verificación
                // del
                // JSON
                verify(servicioPropiedad, times(1)).crearPropiedad(any(DTOPropiedad.class));
        }

        @Test
        public void testActualizarPropiedad() throws Exception {
                DTOPropiedad propiedad = new DTOPropiedad();
                propiedad.setId(1L);
                propiedad.setNombre("Casa de playa");
                propiedad.setUbicacion("Ubicación 1");
                propiedad.setParqueadero(true);
                propiedad.setPiscina(true);
                propiedad.setCuartos(3);
                propiedad.setCamas(5);
                propiedad.setArea(200.5f);
                propiedad.setCapacidad(10);
                propiedad.setDisponible(true);
                propiedad.setPrecioXnoche(300L);
                propiedad.setStatus(1);
                propiedad.setArrendadorId(1L);

                when(servicioPropiedad.actualizarPropiedad(eq(1L), any(DTOPropiedad.class))).thenReturn(propiedad);

                mockMvc.perform(MockMvcRequestBuilders.put("/api/propiedad/actualizarPropiedad/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                                "{\"nombre\":\"Casa de playa\",\"ubicacion\":\"Ubicación 1\",\"parqueadero\":true,\"piscina\":true,\"cuartos\":3,\"camas\":5,\"area\":200.5,\"capacidad\":10,\"disponible\":true,\"precioXnoche\":300.0}")
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(content().json(
                                                "{\"id\":1,\"nombre\":\"Casa de playa\",\"ubicacion\":\"Ubicación 1\",\"parqueadero\":true,\"piscina\":true,\"cuartos\":3,\"camas\":5,\"area\":200.5,\"capacidad\":10,\"disponible\":true,\"precioXnoche\":300.0,\"status\":1,\"arrendadorId\":1}"));

                verify(servicioPropiedad, times(1)).actualizarPropiedad(eq(1L), any(DTOPropiedad.class));
        }

        @Test
        public void testEliminarPropiedad() throws Exception {
                Long id = 1L;
                /*
                 * when(servicioPropiedad.eliminarPropiedad(id))
                 * .thenReturn(ResponseEntity.ok("Propiedad eliminada con éxito."));
                 */

                mockMvc.perform(MockMvcRequestBuilders.delete("/api/propiedad/eliminarPropiedad/{id}", id))
                                .andExpect(status().isOk())
                                .andExpect(content().string("Propiedad eliminada con éxito."));

                verify(servicioPropiedad, times(1)).eliminarPropiedad(id);
        }

        @Test
        public void testEliminarPropiedadNotFound() throws Exception {
                Long id = 1L;
                doThrow(new EntityNotFoundException("Propiedad no encontrada")).when(servicioPropiedad)
                                .eliminarPropiedad(id);

                mockMvc.perform(MockMvcRequestBuilders.delete("/api/propiedad/eliminarPropiedad/{id}", id))
                                .andExpect(status().isNotFound())
                                .andExpect(content().string("Propiedad no encontrada."));
        }

        @Test
        public void testEliminarPropiedadError() throws Exception {
                Long id = 1L;
                doThrow(new RuntimeException("Error interno del servidor")).when(servicioPropiedad)
                                .eliminarPropiedad(id);

                mockMvc.perform(MockMvcRequestBuilders.delete("/api/propiedad/eliminarPropiedad/{id}", id))
                                .andExpect(status().isInternalServerError())
                                .andExpect(content().string("Error al eliminar la propiedad."));
        }

}
