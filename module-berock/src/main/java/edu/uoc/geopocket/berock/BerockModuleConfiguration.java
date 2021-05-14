package edu.uoc.geopocket.berock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
@ComponentScan(basePackages = "edu.uoc.geopocket.berock")
public class BerockModuleConfiguration {

  @PostConstruct
  public void postConstruct(){
    log.info("BEROCK MODULE LOADED!");
  }

}
