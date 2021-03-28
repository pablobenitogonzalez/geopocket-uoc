package edu.uoc.geopocket.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
@EnableWebSecurity
@ConditionalOnProperty(name = "edu.uoc.geopocket.security.enabled", havingValue = "true", matchIfMissing = true)
public class SecurityModuleConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring()
            .antMatchers("/resources/**");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
            .antMatchers("/public/**")
            .permitAll()
            .anyRequest()
            .hasRole("USER")
            .and()
            .formLogin()
            .permitAll();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
            .withUser("user")
            .password("password")
            .roles("USER")
            .and()
            .withUser("admin")
            .password("password")
            .roles("USER", "ADMIN");
  }

  @PostConstruct
  public void postConstruct(){
    log.info("SECURITY MODULE LOADED!");
  }


}
