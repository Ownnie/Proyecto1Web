package com.arrienda.proyecto.servicios;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.arrienda.proyecto.modelos.Arrendador;
import com.arrienda.proyecto.repositorios.*;

@Service
public class ServicioArrendador {

    private final RepositorioArrendador repositorioArrendador;

    @Autowired
    public ServicioArrendador(RepositorioArrendador repositorioArrendador) {
        this.repositorioArrendador = repositorioArrendador;
    }

    public List<Arrendador> traerArrendador() {
        return repositorioArrendador.findAll();
    }

}