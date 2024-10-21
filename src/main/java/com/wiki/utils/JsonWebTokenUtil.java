package com.wiki.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.wiki.models.user.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JsonWebTokenUtil {

   @Value("${security.jwt.key}") //Pasamos la llave creada en app properties
   private String privateKey;

   @Value("${security.jwt.user}")
   private String userGenerator;


   //Esta funcion se usa cuando alguien se logea
   public String createToken(Authentication authentication) {
      // Obtener el correo del usuario desde el objeto Authentication
      String email = authentication.getName();  // Por convención, Spring usa el email o username como principal

      // Obtener los roles del usuario como un arreglo
      String[] roles = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)  // Obtener cada rol
            .toArray(String[]::new);               // Convertir a un arreglo

      // Generar el token JWT
      return JWT.create()
            .withIssuer(userGenerator)                 // Indicar el emisor del token
            .withSubject(email)                        // Correo del usuario como subject
            .withArrayClaim("roles", roles)                 // Agregar los roles como claim
            .withIssuedAt(new Date())                  // Fecha de emisión del token
            .withExpiresAt(new Date(System.currentTimeMillis() + 1800000))  // Expiración (30 min)
            .withJWTId(UUID.randomUUID().toString())   // ID único del token
            .withNotBefore(new Date())                // Token válido inmediatamente
            .sign(Algorithm.HMAC256(privateKey));                         // Firmar el token con el algoritmo HMAC256
   }

   //Esta se usa, cuando alguien se registra
   public String createToken(User user) {

      // Obtener los roles del usuario como un arreglo
      String[] roles = user.getRoles().stream()
            .map(role -> role.getName().name()) // Asegúrate de que Role tenga un metodo getName() que devuelva el Enum
            .toArray(String[]::new);

      // Generar el token JWT
      return JWT.create()
            .withIssuer(userGenerator)                 // Indicar el emisor del token
            .withSubject(user.getEmail())                        // Correo del usuario como subject
            .withArrayClaim("roles", roles)           // Agregar los roles como claim
            .withIssuedAt(new Date())                  // Fecha de emisión del token
            .withExpiresAt(new Date(System.currentTimeMillis() + 1800000))  // Expiración (30 min)
            .withJWTId(UUID.randomUUID().toString())   // ID único del token
            .withNotBefore(new Date())                 // Token válido inmediatamente
            .sign(Algorithm.HMAC256(privateKey));      // Firmar el token con el algoritmo HMAC256
   }

   public DecodedJWT validateToken(String token) {
      try {
         // Verificador del JWT utilizando el algoritmo y el emisor
         JWTVerifier verifier = JWT.require(Algorithm.HMAC256(privateKey)).withIssuer(userGenerator).build();

         // Decodificar y validar el token
         return verifier.verify(token);
      } catch (JWTVerificationException e) {
         throw new JWTVerificationException("Token inválido o expirado: " + e.getMessage());
      }
   }

   public String[] getRolesFromToken(DecodedJWT decodedJWT) {
      Claim rolesClaim = decodedJWT.getClaim("roles");  // Obtener el claim de roles
      if (rolesClaim.asString() != null) {
         return rolesClaim.asString().split(",");  // Dividir la cadena en un arreglo por comas
      }
      return new String[0];  // Retornar un arreglo vacío si no hay roles
   }

   public String extractEmail(DecodedJWT decodedJWT) {
      return decodedJWT.getSubject(); //Nos trae el usuario completo
   }

   public Claim getSpecificClaim(DecodedJWT decodedJWT, String claimName) {
      return decodedJWT.getClaim(claimName); //Obtenemos un claim especifico
   }

   public Map<String, Claim> returnAllClaims(DecodedJWT decodedJWT) {
      return decodedJWT.getClaims(); //Obtenemos todos los claims
   }

}
