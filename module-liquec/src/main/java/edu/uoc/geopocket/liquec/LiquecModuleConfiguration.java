package edu.uoc.geopocket.liquec;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
@ComponentScan(basePackages = "edu.uoc.geopocket.liquec")
public class LiquecModuleConfiguration {

  @PostConstruct
  public void postConstruct(){
    log.info("LIQUEC MODULE LOADED!");
  }

}
