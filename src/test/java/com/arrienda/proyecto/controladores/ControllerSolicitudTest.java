package com.arrienda.proyecto.controladores;
import jakarta.persistence.EntityNotFoundException;

import com.arrienda.proyecto.dtos.DTOArrendatario;
import com.arrienda.proyecto.dtos.DTOPropiedad;
import com.arrienda.proyecto.dtos.DTOSolicitud;
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
import java.sql.Date;
    
@ExtendWith(SpringExtension.class)
@WebMvcTest(ControllerSolicitud.class)
public class ControllerSolicitudTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ControllerSolicitud controllerSolicitud;

    @Test
    public void testGetAllSolicitudes() throws Exception {
        DTOSolicitud solicitud = new DTOSolicitud();
        solicitud.setId(1L);
        solicitud.setFechaLlegada(Date.valueOf("2024-10-12")); 
        solicitud.setFechaPartida(Date.valueOf("2024-10-20"));
        solicitud.setAceptacion(true);
        solicitud.setCantidadPersonas(4);
        solicitud.setStatus(1);
        solicitud.setPropiedad(new DTOPropiedad()); 
        solicitud.setArrendatario(new DTOArrendatario());

        List<DTOSolicitud> solicitudes = Arrays.asList(solicitud);

        when(controllerSolicitud.getAllSolicitudes()).thenReturn(solicitudes);

        mockMvc.perform(MockMvcRequestBuilders.get("/solicitud/solicitudes")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"id\":1,\"fechaLlegada\":\"2024-10-12\",\"fechaPartida\":\"2024-10-20\",\"aceptacion\":true,\"cantidadPersonas\":4,\"status\":1}]"));
    }

    @Test
    public void testGetSolicitud() throws Exception {
        DTOSolicitud solicitud = new DTOSolicitud();
        solicitud.setId(1L);
        solicitud.setFechaLlegada(Date.valueOf("2024-10-12")); 
        solicitud.setFechaPartida(Date.valueOf("2024-10-20"));
        solicitud.setAceptacion(true);
        solicitud.setCantidadPersonas(4);
        solicitud.setStatus(1);
        solicitud.setPropiedad(new DTOPropiedad());
        solicitud.setArrendatario(new DTOArrendatario());

        when(controllerSolicitud.getSolicitud(1L)).thenReturn(solicitud);

        mockMvc.perform(MockMvcRequestBuilders.get("/solicitud/solicitud/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json( "{\"id\":1,\"fechaLlegada\":\"2024-10-12\",\"fechaPartida\":\"2024-10-20\",\"aceptacion\":true,\"cantidadPersonas\":4,\"status\":1,\"propiedad\":{},\"arrendatario\":{}}"));

        verify(controllerSolicitud, times(1)).getSolicitud(1L);
    }

    @Test
    public void testGetSolicitudesByEstado() throws Exception {
        DTOSolicitud solicitud = new DTOSolicitud();
        solicitud.setId(1L);
        solicitud.setFechaLlegada(Date.valueOf("2024-10-12")); 
        solicitud.setFechaPartida(Date.valueOf("2024-10-20"));
        solicitud.setAceptacion(true);
        solicitud.setCantidadPersonas(4);
        solicitud.setStatus(1);
        solicitud.setPropiedad(new DTOPropiedad());
        solicitud.setArrendatario(new DTOArrendatario());

        List<DTOSolicitud> solicitudes = Arrays.asList(solicitud);

        when(controllerSolicitud.getSolicitudesByEstado(1)).thenReturn(solicitudes);

        mockMvc.perform(MockMvcRequestBuilders.get("/solicitud/solicitudes/estado/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(
                        "[{\"id\":1,\"fechaLlegada\":\"2024-10-12\",\"fechaPartida\":\"2024-10-20\",\"aceptacion\":true,\"cantidadPersonas\":4,\"status\":1,\"propiedad\":{},\"arrendatario\":{}}]"));

        verify(controllerSolicitud, times(1)).getSolicitudesByEstado(1);
    }

    @Test
    public void testGetSolicitudesByArrendatarioId() throws Exception {
        DTOSolicitud solicitud = new DTOSolicitud();
        solicitud.setId(1L);
        solicitud.setFechaLlegada(Date.valueOf("2024-10-12")); 
        solicitud.setFechaPartida(Date.valueOf("2024-10-20"));
        solicitud.setAceptacion(true);
        solicitud.setCantidadPersonas(4);
        solicitud.setStatus(1);
        solicitud.setPropiedad(new DTOPropiedad());
        solicitud.setArrendatario(new DTOArrendatario());
    
        List<DTOSolicitud> solicitudes = Arrays.asList(solicitud);
    
        when(controllerSolicitud.getSolicitudesByArrendatarioId(1L)).thenReturn(solicitudes);
    
        mockMvc.perform(MockMvcRequestBuilders.get("/solicitud/solicitudes/arrendatario/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(
                        "[{\"id\":1,\"fechaLlegada\":\"2024-10-12\",\"fechaPartida\":\"2024-10-20\",\"aceptacion\":true,\"cantidadPersonas\":4,\"status\":1,\"propiedad\":{},\"arrendatario\":{}}]"));
    
        verify(controllerSolicitud, times(1)).getSolicitudesByArrendatarioId(1L);
    }

    @Test
    public void testGetSolicitudesByPropiedadId() throws Exception {
        DTOSolicitud solicitud1 = new DTOSolicitud(
            1L,
            Date.valueOf("2024-10-12"),
            Date.valueOf("2024-10-20"),
            true,
            3,
            1,
            new DTOPropiedad(1L, "Casa de playa", "Ubicación 1", true, true, 3, 5, 200.5f, 10, true, 300.0f, 1, 1L),
            new DTOArrendatario(1L, "juan123", "password", "Juan", 1, "juan@example.com", new ArrayList<>(), new ArrayList<>())
        );

        DTOSolicitud solicitud2 = new DTOSolicitud(
            2L,
            Date.valueOf("2024-11-12"),
            Date.valueOf("2024-11-20"),
            false,
            2,
            2,
            new DTOPropiedad(2L, "Apartamento en la ciudad", "Ubicación 2", false, false, 2, 3, 80.0f, 5, true, 150.0f, 1, 2L),
            new DTOArrendatario(2L, "maria123", "password", "Maria", 1, "maria@example.com", new ArrayList<>(), new ArrayList<>())
        );

        List<DTOSolicitud> solicitudes = Arrays.asList(solicitud1, solicitud2);

        when(controllerSolicitud.getSolicitudesByPropiedadId(1L)).thenReturn(solicitudes);

        mockMvc.perform(MockMvcRequestBuilders.get("/solicitud/solicitudes/propiedad/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(
                        "[{\"id\":1,\"fechaLlegada\":\"2024-10-12\",\"fechaPartida\":\"2024-10-20\",\"aceptacion\":true,\"cantidadPersonas\":3,\"status\":1,\"propiedad\":{\"id\":1,\"nombre\":\"Casa de playa\",\"ubicacion\":\"Ubicación 1\",\"parqueadero\":true,\"piscina\":true,\"cuartos\":3,\"camas\":5,\"area\":200.5,\"capacidad\":10,\"disponible\":true,\"precioXnoche\":300.0,\"status\":1,\"arrendadorId\":1},\"arrendatario\":{\"id\":1,\"usuario\":\"juan123\",\"contrasena\":\"password\",\"nombre\":\"Juan\",\"status\":1,\"correo\":\"juan@example.com\",\"propiedades\":[],\"calificaciones\":[]}}," +
                        "{\"id\":2,\"fechaLlegada\":\"2024-11-12\",\"fechaPartida\":\"2024-11-20\",\"aceptacion\":false,\"cantidadPersonas\":2,\"status\":2,\"propiedad\":{\"id\":2,\"nombre\":\"Apartamento en la ciudad\",\"ubicacion\":\"Ubicación 2\",\"parqueadero\":false,\"piscina\":false,\"cuartos\":2,\"camas\":3,\"area\":80.0,\"capacidad\":5,\"disponible\":true,\"precioXnoche\":150.0,\"status\":1,\"arrendadorId\":2},\"arrendatario\":{\"id\":2,\"usuario\":\"maria123\",\"contrasena\":\"password\",\"nombre\":\"Maria\",\"status\":1,\"correo\":\"maria@example.com\",\"propiedades\":[],\"calificaciones\":[]}}]"));
                
        verify(controllerSolicitud, times(1)).getSolicitudesByPropiedadId(1L);
    }

    @Test
    public void testCrearSolicitud() throws Exception {
        DTOSolicitud solicitud = new DTOSolicitud(
            1L,
            Date.valueOf("2024-11-12"),
            Date.valueOf("2024-11-20"),
            true,
            3,
            1,
            new DTOPropiedad(1L, "Casa de playa", "Ubicación 1", true, true, 3, 5, 200.5f, 10, true, 300.0f, 1, 1L),
            new DTOArrendatario(1L, "juan123", "password", "Juan", 1, "juan@example.com", new ArrayList<>(), new ArrayList<>())
        );

        when(controllerSolicitud.crearSolicitud(any(DTOSolicitud.class))).thenReturn(solicitud);

        mockMvc.perform(MockMvcRequestBuilders.post("/solicitud/crearSolicitud")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"fechaLlegada\":\"" + solicitud.getFechaLlegada() + "\",\"fechaPartida\":\"" + solicitud.getFechaPartida() + "\",\"aceptacion\":true,\"cantidadPersonas\":3,\"status\":1,\"propiedad\":{\"id\":1,\"nombre\":\"Casa de playa\",\"ubicacion\":\"Ubicación 1\",\"parqueadero\":true,\"piscina\":true,\"cuartos\":3,\"camas\":5,\"area\":200.5,\"capacidad\":10,\"disponible\":true,\"precioXnoche\":300.0,\"status\":1},\"arrendatario\":{\"id\":1,\"usuario\":\"juan123\",\"contrasena\":\"password\",\"nombre\":\"Juan\",\"status\":1,\"correo\":\"juan@example.com\",\"propiedades\":[],\"calificaciones\":[]}}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(
                    "{\"id\":1,\"fechaLlegada\":\"" + solicitud.getFechaLlegada() + "\",\"fechaPartida\":\"" + solicitud.getFechaPartida() + "\",\"aceptacion\":true,\"cantidadPersonas\":3,\"status\":1,\"propiedad\":{\"id\":1,\"nombre\":\"Casa de playa\",\"ubicacion\":\"Ubicación 1\",\"parqueadero\":true,\"piscina\":true,\"cuartos\":3,\"camas\":5,\"area\":200.5,\"capacidad\":10,\"disponible\":true,\"precioXnoche\":300.0,\"status\":1},\"arrendatario\":{\"id\":1,\"usuario\":\"juan123\",\"contrasena\":\"password\",\"nombre\":\"Juan\",\"status\":1,\"correo\":\"juan@example.com\",\"propiedades\":[],\"calificaciones\":[]}}"));

        verify(controllerSolicitud, times(1)).crearSolicitud(any(DTOSolicitud.class));
    }

    @Test
    public void testActualizarSolicitud() throws Exception {
        DTOSolicitud solicitudActualizada = new DTOSolicitud(
            1L,
            Date.valueOf("2024-11-12"),
            Date.valueOf("2024-11-20"),
            false,
            4,
            2,
            new DTOPropiedad(1L, "Casa de playa", "Ubicación 1", true, true, 3, 5, 200.5f, 10, true, 300.0f, 1, 1L),
            new DTOArrendatario(1L, "juan123", "password", "Juan", 1, "juan@example.com", new ArrayList<>(), new ArrayList<>())
        );

        when(controllerSolicitud.actualizarSolicitud(eq(1L), any(DTOSolicitud.class))).thenReturn(solicitudActualizada);

        mockMvc.perform(MockMvcRequestBuilders.put("/solicitud/actualizarSolicitud/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"fechaLlegada\":\"" + solicitudActualizada.getFechaLlegada() + "\",\"fechaPartida\":\"" + solicitudActualizada.getFechaPartida() + "\",\"aceptacion\":false,\"cantidadPersonas\":4,\"status\":2,\"propiedad\":{\"id\":1,\"nombre\":\"Casa de playa\",\"ubicacion\":\"Ubicación 1\",\"parqueadero\":true,\"piscina\":true,\"cuartos\":3,\"camas\":5,\"area\":200.5,\"capacidad\":10,\"disponible\":true,\"precioXnoche\":300.0,\"status\":1},\"arrendatario\":{\"id\":1,\"usuario\":\"juan123\",\"contrasena\":\"password\",\"nombre\":\"Juan\",\"status\":1,\"correo\":\"juan@example.com\",\"propiedades\":[],\"calificaciones\":[]}}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(
                    "{\"id\":1,\"fechaLlegada\":\"" + solicitudActualizada.getFechaLlegada() + "\",\"fechaPartida\":\"" + solicitudActualizada.getFechaPartida() + "\",\"aceptacion\":false,\"cantidadPersonas\":4,\"status\":2,\"propiedad\":{\"id\":1,\"nombre\":\"Casa de playa\",\"ubicacion\":\"Ubicación 1\",\"parqueadero\":true,\"piscina\":true,\"cuartos\":3,\"camas\":5,\"area\":200.5,\"capacidad\":10,\"disponible\":true,\"precioXnoche\":300.0,\"status\":1},\"arrendatario\":{\"id\":1,\"usuario\":\"juan123\",\"contrasena\":\"password\",\"nombre\":\"Juan\",\"status\":1,\"correo\":\"juan@example.com\",\"propiedades\":[],\"calificaciones\":[]}}"));

        verify(controllerSolicitud, times(1)).actualizarSolicitud(eq(1L), any(DTOSolicitud.class));
    }

    @Test
    public void testEliminarSolicitud() throws Exception {
        Long id = 1L;
        when(controllerSolicitud.eliminarSolicitud(id))
                .thenReturn(ResponseEntity.ok("Solicitud eliminada con éxito."));

        mockMvc.perform(MockMvcRequestBuilders.delete("/solicitud/eliminarSolicitud/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().string("Solicitud eliminada con éxito."));

        verify(controllerSolicitud, times(1)).eliminarSolicitud(id);
    }

    @Test
    public void testEliminarSolicitudNotFound() throws Exception {
        Long id = 1L;
        when(controllerSolicitud.eliminarSolicitud(id))
                .thenThrow(new EntityNotFoundException("Solicitud no encontrada"));

        mockMvc.perform(MockMvcRequestBuilders.delete("/solicitud/eliminarSolicitud/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Entidad no encontrada."));
    }

    @Test
    public void testEliminarSolicitudError() throws Exception {
        Long id = 1L;
        when(controllerSolicitud.eliminarSolicitud(id))
                .thenThrow(new RuntimeException("Error interno del servidor"));

        mockMvc.perform(MockMvcRequestBuilders.delete("/solicitud/eliminarSolicitud/{id}", id))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error interno del servidor."));
    }
}
