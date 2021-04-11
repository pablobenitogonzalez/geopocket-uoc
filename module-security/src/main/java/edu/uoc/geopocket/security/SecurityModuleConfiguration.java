package edu.uoc.geopocket.security;

import edu.uoc.geopocket.security.common.GeoPocketRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;

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
        .withUser("user")
        .password(passwordEncoder().encode("123456"))
        .roles(GeoPocketRole.ROLE_USER.getRoleName());
    auth.inMemoryAuthentication()
        .withUser("admin")
        .password(passwordEncoder().encode("123456"))
        .roles(GeoPocketRole.ROLE_USER.getRoleName(), GeoPocketRole.ROLE_ADMIN.getRoleName());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable().authorizeRequests()
        .antMatchers("/public/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .httpBasic()
        .authenticationEntryPoint(authenticationEntryPoint);
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
