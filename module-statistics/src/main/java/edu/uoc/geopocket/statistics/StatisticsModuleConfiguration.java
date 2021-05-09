package edu.uoc.geopocket.statistics;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
@ComponentScan(basePackages = "edu.uoc.geopocket.statistics")
public class StatisticsModuleConfiguration {

  @PostConstruct
  public void postConstruct(){
    log.info("STATISTICS MODULE LOADED!");
  }
}
