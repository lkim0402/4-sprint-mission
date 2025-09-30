package com.sprint.mission.discodeit.config;

import com.sprint.mission.discodeit.auth.CustomAccessDeniedHandler;
import com.sprint.mission.discodeit.auth.CustomAuthenticationEntryPoint;
import com.sprint.mission.discodeit.auth.DiscodeitUserDetailsService;
import com.sprint.mission.discodeit.auth.LoginFailureHandler;
import com.sprint.mission.discodeit.auth.LoginSuccessHandler;
import com.sprint.mission.discodeit.entity.Role;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.repository.UserRepository;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final LoginSuccessHandler loginSuccessHandler;
  private final LoginFailureHandler loginFailureHandler;
  private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
  private final CustomAccessDeniedHandler customAccessDeniedHandler;

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
            // disabling CSRF protection specifically for the H2 console path
            .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"))

        )
//        .formLogin(Customizer.withDefaults())
        .formLogin(login -> login
            // tells Spring Security to listen for POST requests to "/api/auth/login"
            // caught by UsernamePasswordAuthenticationFilter
            .loginProcessingUrl("/api/auth/login")
            .successHandler(loginSuccessHandler)    // 로그인 성공 후 핸들러
            .failureHandler(loginFailureHandler)      // 로그인 실패 후 핸들러
        )
        .logout(logout -> logout
            .logoutUrl("/api/auth/logout")
            .logoutSuccessHandler(
                // Designed specifically for REST APIs
                // Only job is to return a specific HTTP status code when logout successful
                new HttpStatusReturningLogoutSuccessHandler(HttpStatus.NO_CONTENT))
        )
        // allowing the H2 console to be loaded in a frame
        .headers(headers -> headers
            .frameOptions(FrameOptionsConfig::disable)
        )
        .authorizeHttpRequests(auth -> auth

                // Root and HTML files
                .requestMatchers("/").permitAll()
                .requestMatchers("/index.html").permitAll()  // ADD THIS!

                // Static resources
                .requestMatchers("/favicon.ico").permitAll()
                .requestMatchers("/static/**").permitAll()
                .requestMatchers("/assets/**").permitAll()
                .requestMatchers("/*.js").permitAll()
                .requestMatchers("/*.css").permitAll()
                .requestMatchers("/*.png").permitAll()
                .requestMatchers("/*.svg").permitAll()
                .requestMatchers("/*.jpg").permitAll()

                // these don't need authentication
                .requestMatchers("/api/users").permitAll() // 회원가입
//            .requestMatchers("/api/auth/csrf-token").permitAll() // Csrf Token 발급
//            .requestMatchers("/api/auth/login").permitAll() // 로그인
//            .requestMatchers("/api/auth/logout").permitAll() // 로그아웃

                .requestMatchers("/.well-known/**").permitAll()

                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/h2-console/**").permitAll() // H2 콘솔
                .requestMatchers("/swagger-ui/**").permitAll() // Swagger UI
                .requestMatchers("/v3/api-docs/**").permitAll()  // Swagger API docs
                .requestMatchers("/actuator/**").permitAll()  // Spring Actuator
                // all other should be authenticated

                .anyRequest().authenticated()
        )
        .exceptionHandling(ex -> ex
            // authentication failure
            .authenticationEntryPoint(customAuthenticationEntryPoint)
            // authentication succeeded but cannot access resource (forbidden 403)
            .accessDeniedHandler(customAccessDeniedHandler)
        )

    ;
    return http.build();
  }

  // encrypts
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  @Transactional
  // we manually initialize DiscodeitUserDetailsService
  // we do this because we have custom logic (initializing admin) before returning USerDetails
  public UserDetailsService userDetailsService(UserRepository userRepository,
      UserMapper userMapper,
      PasswordEncoder passwordEncoder) {

    if (!userRepository.existsByRole(Role.ADMIN)) {
      User admin = new User(
          "admin",
          "admin@discodeit.com",
          passwordEncoder.encode("1234"),
          null,
          Role.ADMIN
      );
      userRepository.save(admin);
      UserStatus userStatus = new UserStatus(admin, Instant.now());
      admin.setStatus(userStatus);
    }
    return new DiscodeitUserDetailsService(userRepository, userMapper);
  }

  @Bean
  public RoleHierarchy roleHierarchy() {
    RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
    // Admin > Channel Manager > User
    roleHierarchy.setHierarchy(
        "ROLE_ADMIN > ROLE_CHANNEL_MANAGER\n" +
            "ROLE_CHANNEL_MANAGER > ROLE_USER"
    );
    return roleHierarchy;
  }

  @Bean
  public static MethodSecurityExpressionHandler methodSecurityExpressionHandler(
      RoleHierarchy roleHierarchy) {
    DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
    handler.setRoleHierarchy(roleHierarchy);
    return handler;
  }


}
