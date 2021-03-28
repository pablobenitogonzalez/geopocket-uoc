package edu.uoc.geopocket.app;

import edu.uoc.geopocket.common.EnableCommonModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@EnableCommonModule
//@EnableLiquecModule
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class GeoPocket {

	public static void main(String[] args) {
		SpringApplication.run(GeoPocket.class, args);
	}

}

