package com.sprint.mission.discodeit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http
        .csrf(csrf -> csrf
            // decides *where* to save the CSRF token
            // saves in HttpSessionCsrfTokenRepository by default (in server's session)
            // CookieCsrfTokenRepository saves the token in client browser's cookie
            // httpOnlyFalse allows JS to read the cookie because
            //   client's script needs to read the cookie and send it thru HTTP header
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            // decides *how* to read/handle CSRF tokens -> SpaCsrfTokenRequestHandler
            .csrfTokenRequestHandler(new SpaCsrfTokenRequestHandler())
        );

    return http.build();
  }

}
