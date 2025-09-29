package com.sprint.mission.discodeit.config;

import com.sprint.mission.discodeit.auth.LoginFailureHandler;
import com.sprint.mission.discodeit.auth.LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final LoginSuccessHandler loginSuccessHandler;
  private final LoginFailureHandler loginFailureHandler;

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
        )
        .formLogin(Customizer.withDefaults())
        .formLogin(login -> login
            // tells Spring Security to listen for POST requests to "/api/auth/login"
            // caught by UsernamePasswordAuthenticationFilter
            .loginProcessingUrl("/api/auth/login")
            .successHandler(loginSuccessHandler)    // 로그인 성공 후 핸들러
            .failureHandler(loginFailureHandler)      // 로그인 실패 후 핸들러
        )
    ;

    return http.build();
  }

  // encrypts
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }


}
