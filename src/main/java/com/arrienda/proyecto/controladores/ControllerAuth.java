package com.arrienda.proyecto.controladores;

import com.arrienda.proyecto.dtos.DTOLogin;
import com.arrienda.proyecto.dtos.DTOArrendador;
import com.arrienda.proyecto.dtos.DTOArrendadorContrasena;
import com.arrienda.proyecto.dtos.DTOArrendatario;
import com.arrienda.proyecto.dtos.DTOArrendatarioContrasena;
import com.arrienda.proyecto.servicios.ServicioArrendador;
import com.arrienda.proyecto.servicios.ServicioArrendatario;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class ControllerAuth {
    private final ServicioArrendador servicioArrendador;
    private final ServicioArrendatario servicioArrendatario;
    private static final Log log = LogFactory.getLog(ControllerAuth.class);

    public ControllerAuth(ServicioArrendador servicioArrendador, ServicioArrendatario servicioArrendatario) {
        this.servicioArrendador = servicioArrendador;
        this.servicioArrendatario = servicioArrendatario;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody DTOLogin dtoLogin) {
        log.info("Solicitud recibida: " + dtoLogin);

        Map<String, String> response = new HashMap<>();
        if (dtoLogin.getType() == 1) {
            log.info("Tipo de usuario: Arrendador");
            String username = dtoLogin.getUsername();
            String password = dtoLogin.getPassword();
            String token = servicioArrendador.login(username, password);
            response.put("token", token);
            log.info("Token generado para el arrendador: " + token);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else if (dtoLogin.getType() == 2) {
            log.info("Tipo de usuario: Arrendatario");
            String username = dtoLogin.getUsername();
            String password = dtoLogin.getPassword();
            String token = servicioArrendatario.login(username, password);
            response.put("token", token);
            log.info("Token generado para el arrendatario: " + token);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.put("error", "Usuario o contraseña incorrectos");
        log.error("Error en las credenciales");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/register/arrendador")
    public ResponseEntity<DTOArrendador> registerArrendador(@RequestBody DTOArrendadorContrasena dtoArrendador) {
        DTOArrendador arrendador = servicioArrendador.crearArrendador(dtoArrendador);
        return new ResponseEntity<>(arrendador, HttpStatus.CREATED);
    }

    @PostMapping("/register/arrendatario")
    public ResponseEntity<DTOArrendatario> registerArrendatario(
            @RequestBody DTOArrendatarioContrasena dtoArrendatario) {
        DTOArrendatario arrendatario = servicioArrendatario.createArrendatario(dtoArrendatario);
        return new ResponseEntity<>(arrendatario, HttpStatus.CREATED);
    }

}
