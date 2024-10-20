package com.arrienda.proyecto.repositorios;

import java.util.List;
import java.util.Optional;

import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import com.arrienda.proyecto.modelos.Solicitud;

public interface RepositorioSolicitud extends JpaRepository<Solicitud, Long> {

    Optional<Solicitud> findById(Long id);

    List<Solicitud> findByStatus(int estado);

    List<Solicitud> findByArrendatarioId(Long arrendatarioId);

    List<Solicitud> findByPropiedadId(Long propiedadId);

    List<Solicitud> findSolicitudesByArrendatarioId(long id);

    boolean existsByPropiedadIdAndAceptacionAndFechaLlegadaLessThanEqualAndFechaPartidaGreaterThanEqual(
            Long propiedadId, boolean aceptacion, Date fechaPartida, Date fechaLlegada);

}
