package edu.uoc.geopocket.project;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import(ProjectModuleConfiguration.class)
@Configuration
public @interface EnableProjectModule {
}
