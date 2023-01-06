package com.winterhack.wiki.Configuration;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.winterhack.wiki.JWT.JwtAccessDeniedHandler;
import com.winterhack.wiki.JWT.JwtAuthenticationEntryPoint;
import com.winterhack.wiki.JWT.TokenProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

  private final TokenProvider tokenProvider;
  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
  
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring().antMatchers("/favicon.ico");
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
      CorsConfiguration configuration = new CorsConfiguration();
      
      configuration.setAllowedOriginPatterns(Arrays.asList("*"));
      configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT"));
      configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
      configuration.setAllowCredentials(true);
      
      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      source.registerCorsConfiguration("/**", configuration);
      
      return source;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
      .csrf().disable()

      // 401, 403 Exception Handling
      .exceptionHandling()
      .authenticationEntryPoint(jwtAuthenticationEntryPoint)
      .accessDeniedHandler(jwtAccessDeniedHandler)

      // No session
      .and()
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

      // Request authorize setting for request with HttpServletRequest
      .and()
      .authorizeRequests()
      .antMatchers("/hello").permitAll()
      .antMatchers("/auth").permitAll()
      .antMatchers("/secure").authenticated()
      .antMatchers("/docs/star/**").authenticated()

      // JwtSecurityConfig
      .and()
      .apply(new JwtSecurityConfiguration(tokenProvider))

      .and()

      .cors()
      .and()

      .build();
  }

}
