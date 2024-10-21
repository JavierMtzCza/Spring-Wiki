package com.wiki.securityConfig;

import com.wiki.services.user.UserAuthServiceImpl;
import com.wiki.services.user.UserServiceImpl;
import com.wiki.utils.JsonWebTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

   @Autowired
   private JsonWebTokenUtil tokenUtil;

   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
      return httpSecurity
            .addFilterBefore(new JsonWebTokenFilter(tokenUtil), BasicAuthenticationFilter.class)
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(http -> {
               http.requestMatchers(HttpMethod.POST, "/auth/**").permitAll();

               http.requestMatchers(HttpMethod.POST, "/topic/**").hasAnyRole("ADMIN", "USER");

               http.anyRequest().denyAll();
            })
            .build();
   }

   @Bean
   public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
      return authenticationConfiguration.getAuthenticationManager();
   }

   @Bean
   public AuthenticationProvider authenticationProvider(UserAuthServiceImpl userAuthService) {
      DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
      daoAuthenticationProvider.setUserDetailsService(userAuthService);
      daoAuthenticationProvider.setPasswordEncoder(pwdEncoder());

      return daoAuthenticationProvider;
   }

   @Bean
   public PasswordEncoder pwdEncoder() {
      return new BCryptPasswordEncoder();
   }

}
