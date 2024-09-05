package com.arrienda.proyecto.controladores;

import com.arrienda.proyecto.dtos.*;
import com.arrienda.proyecto.servicios.*;
import com.arrienda.proyecto.utilidades.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.arrienda.proyecto.modelos.Usuario;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ServicioUsuarioDetalle usuarioDetalleServicio;

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        try {
            // Autenticar usuario
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsuario(), request.getContrasena()));

            // Cargar los detalles del usuario
            UserDetails userDetails = usuarioDetalleServicio.loadUserByUsername(request.getUsuario());

            // Generar el token JWT
            String token = jwtUtil.generateToken(userDetails);

            return ResponseEntity.ok(new AuthResponse(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body(null);
        }
    }

    @PostMapping("/registro")
    public ResponseEntity<String> register(@RequestBody DTOUsuario usuarioDTO) {
        Usuario nuevoUsuario = usuarioDetalleServicio.registrarUsuario(usuarioDTO);
        return ResponseEntity.ok("Usuario registrado con éxito con ID: " + nuevoUsuario.getId());
    }
}
