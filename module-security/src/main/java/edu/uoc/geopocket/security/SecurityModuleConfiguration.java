package edu.uoc.geopocket.security;

import edu.uoc.geopocket.security.common.GeoPocketRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;

@Slf4j
@Configuration
@EnableWebSecurity
@ConditionalOnProperty(name = "edu.uoc.geopocket.security.enabled", havingValue = "true", matchIfMissing = true)
public class SecurityModuleConfiguration extends WebSecurityConfigurerAdapter {

  private final GeopocketBasicAuthenticationEntryPoint authenticationEntryPoint;

  @Autowired
  public SecurityModuleConfiguration(GeopocketBasicAuthenticationEntryPoint authenticationEntryPoint) {
    this.authenticationEntryPoint = authenticationEntryPoint;
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
            .withUser("user1")
            .password(passwordEncoder().encode("123456"))
            .roles(GeoPocketRole.ROLE_USER.getRoleName());
    auth.inMemoryAuthentication()
            .withUser("user2")
            .password(passwordEncoder().encode("123456"))
            .roles(GeoPocketRole.ROLE_USER.getRoleName());
    auth.inMemoryAuthentication()
            .withUser("admin")
            .password(passwordEncoder().encode("123456"))
            .roles(GeoPocketRole.ROLE_USER.getRoleName(), GeoPocketRole.ROLE_ADMIN.getRoleName());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable()
            .authorizeRequests()
            .antMatchers("/login").permitAll()
            .antMatchers("/public/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .httpBasic()
            .authenticationEntryPoint(authenticationEntryPoint);
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    final CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Collections.singletonList("*"));
    configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
    // setAllowCredentials(true) is important, otherwise:
    // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
    configuration.setAllowCredentials(true);
    // setAllowedHeaders is important! Without it, OPTIONS preflight request
    // will fail with 403 Invalid CORS request
    configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @PostConstruct
  public void postConstruct(){
    log.info("SECURITY MODULE LOADED!");
  }


}
