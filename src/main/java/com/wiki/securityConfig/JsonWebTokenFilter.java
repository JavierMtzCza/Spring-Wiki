package com.wiki.securityConfig;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.wiki.utils.JsonWebTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class JsonWebTokenFilter extends OncePerRequestFilter {

   private JsonWebTokenUtil tokenUtil;

   public JsonWebTokenFilter(JsonWebTokenUtil jsonWebTokenUtil) {
      this.tokenUtil = jsonWebTokenUtil;
   }

   @Override
   protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

      String tokenRequest = request.getHeader(HttpHeaders.AUTHORIZATION);

      if (tokenRequest != null && tokenRequest.startsWith("Bearer ")) {
         try {
            tokenRequest = tokenRequest.substring(7); //Quitamos el Bearer
            DecodedJWT validatedToken = tokenUtil.validateToken(tokenRequest);

            //Obtenemos los datos
            String email = tokenUtil.extractEmail(validatedToken);
            String[] roles = tokenUtil.getRolesFromToken(validatedToken);

            //Convertir los roles en autoridades de Spring Security
            List<SimpleGrantedAuthority> authorities = Arrays.stream(roles).map(SimpleGrantedAuthority::new).toList();  //Creamos un athoritie por cada rol

            //Creamos un objeto de autenticación
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, null, authorities);

            //Establecer el contexto de seguridad con el token de autenticación
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
         } catch (Exception e) {
            // En caso de error en la validación, limpiar el contexto de seguridad
            SecurityContextHolder.clearContext();
            System.out.println("Error al validar el token: " + e.getMessage());
         }
      }
      filterChain.doFilter(request, response); //Si el token el null, continua con el siguiente filtro
   }

}
