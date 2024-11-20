package com.arrienda.proyecto.seguridad.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTTokenService {
    private SecretKey obtenerClaveFirma() {
        String claveSecreta = "1314a9b4e0b2ef5194a06d3cf6af1a4ee689a0ea1841d58c6299a58d784e70a0";
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(claveSecreta));
    }

    public Claims extraerTodosLosClaims(String tokenJWT) {
        return Jwts.parser().verifyWith(obtenerClaveFirma()).build().parseSignedClaims(tokenJWT).getPayload();
    }

    public <T> T extraerClaim(String tokenJWT, Function<Claims, T> resolverDeClaims) {
        final Claims claims = extraerTodosLosClaims(tokenJWT);
        return resolverDeClaims.apply(claims);
    }

    public String extraerNombreUsuario(String tokenJWT) {
        return extraerClaim(tokenJWT, Claims::getSubject);
    }

    public Date extraerFechaExpiracion(String tokenJWT) {
        return extraerClaim(tokenJWT, Claims::getExpiration);
    }

    public Long extraerIdentificacion(String tokenJWT) {
        return extraerClaim(tokenJWT, claims -> claims.get("id", Long.class));
    }

    public Boolean estaTokenExpirado(String tokenJWT) {
        return extraerFechaExpiracion(tokenJWT).before(new Date());
    }

    public String generarToken(Long identificacion, String nombreUsuario) {
        Map<String, Object> datos = new HashMap<>();
        datos.put("id", identificacion);
        return Jwts.builder()
                .setClaims(datos)
                .setSubject(nombreUsuario)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 100 * 60 * 60 * 10))
                .signWith(obtenerClaveFirma())
                .compact();
    }

    public Boolean validarToken(String tokenJWT, UserDetails detallesUsuario) {
        final String nombreUsuario = extraerNombreUsuario(tokenJWT);
        return (nombreUsuario.equals(detallesUsuario.getUsername()) && !estaTokenExpirado(tokenJWT));
    }

}