package edu.uoc.geopocket.statistics;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import(StatisticsModuleConfiguration.class)
@Configuration
public @interface EnableStatisticsModule {
}
