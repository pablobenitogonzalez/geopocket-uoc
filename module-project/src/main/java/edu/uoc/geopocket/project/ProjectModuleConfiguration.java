package edu.uoc.geopocket.project;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
@ComponentScan(basePackages = "edu.uoc.geopocket.project")
public class ProjectModuleConfiguration {

  @PostConstruct
  public void postConstruct(){
    log.info("PROJECT MODULE LOADED!");
  }

}
