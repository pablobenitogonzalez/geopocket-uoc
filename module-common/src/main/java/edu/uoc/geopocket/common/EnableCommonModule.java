package edu.uoc.geopocket.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import(CommonModuleConfiguration.class)
@Configuration
public @interface EnableCommonModule {
}
