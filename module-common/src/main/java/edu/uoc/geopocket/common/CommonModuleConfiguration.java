package edu.uoc.geopocket.common;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
@ComponentScan(basePackages = "edu.uoc.geopocket.common")
public class CommonModuleConfiguration {

  @PostConstruct
  public void postConstruct(){
    log.info("COMMON MODULE LOADED!");
  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }
}
