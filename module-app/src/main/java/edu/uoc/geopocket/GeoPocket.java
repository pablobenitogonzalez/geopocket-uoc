package edu.uoc.geopocket;

import edu.uoc.geopocket.common.EnableCommonModule;
import edu.uoc.geopocket.liquec.EnableLiquecModule;
import edu.uoc.geopocket.project.EnableProjectModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@EnableCommonModule
@EnableProjectModule
@EnableLiquecModule
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class GeoPocket {

	public static void main(String[] args) {
		SpringApplication.run(GeoPocket.class, args);
	}

}

