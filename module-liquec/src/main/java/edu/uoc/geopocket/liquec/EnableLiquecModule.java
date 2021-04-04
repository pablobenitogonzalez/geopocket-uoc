package edu.uoc.geopocket.liquec;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import(LiquecModuleConfiguration.class)
@Configuration
public @interface EnableLiquecModule {
}
