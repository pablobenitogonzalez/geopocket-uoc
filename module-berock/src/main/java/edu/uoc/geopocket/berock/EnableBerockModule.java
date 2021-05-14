package edu.uoc.geopocket.berock;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import(BerockModuleConfiguration.class)
@Configuration
public @interface EnableBerockModule {
}
