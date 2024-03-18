package io.superson.trelloproject.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.superson.trelloproject.global.filter.AuthorizationFilter;
import io.superson.trelloproject.global.impl.UserDetailsServiceImpl;
import io.superson.trelloproject.global.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

  private final JwtUtil jwtUtil;
  private final ObjectMapper objectMapper;
  private final UserDetailsServiceImpl userDetailsServiceImpl;


  //  Filter 한바퀴 돌림
  @Bean
  public AuthorizationFilter authorizationFilter() {
    return new AuthorizationFilter(jwtUtil, objectMapper, userDetailsServiceImpl);
  }


  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  // PasswordEncoder 중에 BCryptPasswordEncoder 사용


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
      throws Exception {

    //CSRF 설정하기
    httpSecurity.csrf((csrf) -> csrf.disable());

    httpSecurity.sessionManagement((sessionManagement) ->
        sessionManagement.sessionCreationPolicy(
            SessionCreationPolicy.STATELESS));

    httpSecurity.authorizeHttpRequests((authorizeHttpRequests) ->
        authorizeHttpRequests
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
            .requestMatchers("/users/login").permitAll() // permitAll() : 접근 허가
            .requestMatchers("/users/signup").permitAll()
            .anyRequest().authenticated() // authenticated() : jwt 인증 필요함
    );

    httpSecurity.addFilterBefore(authorizationFilter(),
        UsernamePasswordAuthenticationFilter.class);

    return httpSecurity.build();
  }
}
