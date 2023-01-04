package com.winterhack.wiki.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
  
  @Bean
  protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // http
    //   .authorizeRequests()
    //   .antMatchers(
    //     "/hello"
    //   )
    //   .permitAll();

    http
      .authorizeRequests()
      .anyRequest()
      .permitAll();

    // http
    //   .authorizeRequests()
    //   .anyRequest()
    //   .authenticated();

    http.csrf().disable();

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
