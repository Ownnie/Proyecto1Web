package com.arrienda.proyecto.servicios;

import java.util.*;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.security.authentication.BadCredentialsException;

import com.arrienda.proyecto.dtos.*;
import com.arrienda.proyecto.modelos.*;
import com.arrienda.proyecto.repositorios.*;
import com.arrienda.proyecto.seguridad.CustomUserDetailService;
import com.arrienda.proyecto.seguridad.jwt.JWTTokenService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ServicioArrendador {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private final RepositorioArrendador repositorioArrendador;

    @Autowired
    private RepositorioPropiedad repositorioPropiedad;

    @Autowired
    private RepositorioCalificacion repositorioCalificacion;

    @Autowired
    private RepositorioSolicitud repositorioSolicitud;

    @Autowired
    private RepositorioArrendatario repositorioArrendatario;

    private final JWTTokenService jwtTokenService;
    private final CustomUserDetailService customUserDetailService;
    private final AuthenticationManager authenticationManager;
    private static final Log log = LogFactory.getLog(ServicioArrendador.class);

    public ServicioArrendador(RepositorioArrendador repositorioArrendador, JWTTokenService jwtTokenService,
            CustomUserDetailService customUserDetailService, AuthenticationManager authenticationManager) {
        this.repositorioArrendador = repositorioArrendador;
        this.jwtTokenService = jwtTokenService;
        this.customUserDetailService = customUserDetailService;
        this.authenticationManager = authenticationManager;
    }

    public List<DTOArrendador> traerArrendadores() {
        try {
            List<DTOArrendador> arrendadores = repositorioArrendador.findAll().stream()
                    .map(arrendador -> {
                        DTOArrendador dtoArrendador = modelMapper.map(arrendador, DTOArrendador.class);

                        List<Propiedad> propiedades = repositorioPropiedad
                                .findPropiedadesByArrendadorId(arrendador.getId());
                        List<DTOPropiedad> dtoPropiedades = propiedades.stream()
                                .map(propiedad -> modelMapper.map(propiedad, DTOPropiedad.class))
                                .collect(Collectors.toList());
                        dtoArrendador.setPropiedades(dtoPropiedades);

                        List<Calificacion> calificaciones = repositorioCalificacion
                                .findByIdCalificadoAndIdTipo(arrendador.getId(), 0);
                        List<DTOCalificacion> dtoCalificaciones = calificaciones.stream()
                                .map(calificacion -> modelMapper.map(calificacion, DTOCalificacion.class))
                                .collect(Collectors.toList());
                        dtoArrendador.setCalificaciones(dtoCalificaciones);

                        return dtoArrendador;
                    })
                    .collect(Collectors.toList());
            return arrendadores;
        } catch (Exception e) {
            throw e;
        }
    }

    public DTOArrendador traerArrendador(Long id) {
        try {
            Arrendador arrendador = repositorioArrendador.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Arrendador no encontrado"));
            DTOArrendador dtoArrendador = modelMapper.map(arrendador, DTOArrendador.class);

            List<Propiedad> propiedades = repositorioPropiedad.findPropiedadesByArrendadorId(arrendador.getId());
            List<DTOPropiedad> dtoPropiedades = propiedades.stream()
                    .map(propiedad -> modelMapper.map(propiedad, DTOPropiedad.class))
                    .collect(Collectors.toList());
            dtoArrendador.setPropiedades(dtoPropiedades);

            List<Calificacion> calificaciones = repositorioCalificacion.findByIdCalificadoAndIdTipo(arrendador.getId(),
                    0);
            List<DTOCalificacion> dtoCalificaciones = calificaciones.stream()
                    .map(calificacion -> modelMapper.map(calificacion, DTOCalificacion.class))
                    .collect(Collectors.toList());
            dtoArrendador.setCalificaciones(dtoCalificaciones);

            return dtoArrendador;
        } catch (Exception e) {
            throw e;
        }
    }

    public DTOArrendador crearArrendador(DTOArrendadorContrasena dtoArrendador) {
        if (repositorioArrendatario.existsByUsuario(dtoArrendador.getUsuario()) ||
                repositorioArrendador.existsByUsuario(dtoArrendador.getUsuario())) {
            throw new IllegalArgumentException("El arrendatario con este usuario ya existe.");
        }

        initializeFields(dtoArrendador);

        Arrendador arrendador = modelMapper.map(dtoArrendador, Arrendador.class);
        Arrendador savedArrendador = repositorioArrendador.save(arrendador);
        return modelMapper.map(savedArrendador, DTOArrendador.class);
    }

    public DTOArrendador actualizarArrendador(Long id, DTOArrendadorContrasena dtoArrendador) {
        Arrendador existingArrendador = repositorioArrendador.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Arrendador no encontrado"));

        modelMapper.map(dtoArrendador, existingArrendador);
        Arrendador updatedArrendador = repositorioArrendador.save(existingArrendador);
        return modelMapper.map(updatedArrendador, DTOArrendador.class);
    }

    private void initializeFields(DTOArrendadorContrasena dtoArrendador) {
        if (dtoArrendador.getUsuario() == null) {
            dtoArrendador.setUsuario("");
        }
        if (dtoArrendador.getNombre() == null) {
            dtoArrendador.setNombre("");
        }
        if (dtoArrendador.getCorreo() == null) {
            dtoArrendador.setCorreo("");
        }
        if (dtoArrendador.getCalificacionPromedio() == 0.0f) {
            dtoArrendador.setCalificacionPromedio(0.0f);
        }
        if (dtoArrendador.getStatus() == 0) {
            dtoArrendador.setStatus(0);
        }
        if (dtoArrendador.getPropiedades() == null) {
            dtoArrendador.setPropiedades(new ArrayList<>());
        }
        if (dtoArrendador.getCalificaciones() == null) {
            dtoArrendador.setCalificaciones(new ArrayList<>());
        }
    }

    @Transactional
    public void eliminarArrendador(Long id) {
        try {
            Arrendador existingArrendador = repositorioArrendador.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Arrendador no encontrado"));

            List<Propiedad> propiedades = repositorioPropiedad
                    .findPropiedadesByArrendadorId(existingArrendador.getId());

            for (Propiedad propiedad : propiedades) {
                List<Calificacion> calificacionesPropiedad = repositorioCalificacion
                        .findByIdCalificadoAndIdTipo(propiedad.getId(), 2);
                repositorioCalificacion.deleteAll(calificacionesPropiedad);

                List<Solicitud> solicitudesPropiedad = repositorioSolicitud.findByPropiedadId(propiedad.getId());
                repositorioSolicitud.deleteAll(solicitudesPropiedad);
            }

            repositorioPropiedad.deleteAll(propiedades);

            List<Calificacion> calificacionesArrendador = repositorioCalificacion
                    .findByIdCalificadoAndIdTipo(existingArrendador.getId(), 0);
            repositorioCalificacion.deleteAll(calificacionesArrendador);

            repositorioArrendador.delete(existingArrendador);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el arrendador", e);
        }
    }

    void actualizarPromedioCalificacion(Long id, float calificacionPromedio) {
        Arrendador arrendador = repositorioArrendador.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Arrendador no encontrado"));
        arrendador.setCalificacionPromedio(calificacionPromedio);
        repositorioArrendador.save(arrendador);
    }

    public String login(String username, String password) {
        try {
            log.info("Iniciando autenticación para el usuario: " + username);
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
            log.info("Autenticación exitosa para el usuario: " + username);

            if (authentication.isAuthenticated()) {
                return jwtTokenService.generarToken(customUserDetailService.getArrendadorDetail().getId(),
                        customUserDetailService.getArrendadorDetail().getUsuario());
            } else {
                log.error("Autenticación fallida para el usuario: " + username);
                throw new BadCredentialsException("Usuario o contraseña incorrectos");
            }
        } catch (Exception e) {
            log.error("Error durante la autenticación: " + e.getMessage(), e);
            throw e;
        }
    }

    public DTOArrendador autorizacion(Authentication auth) {
        Optional<Arrendador> arrendador = repositorioArrendador.findByUsuario(auth.getName());

        if (arrendador.isPresent()) {
            return modelMapper.map(arrendador, DTOArrendador.class);
        } else {
            throw new EntityNotFoundException("Arrendador no encontrado");

        }
    }
}