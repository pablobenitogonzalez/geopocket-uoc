package edu.uoc.geopocket;

import edu.uoc.geopocket.berock.EnableBerockModule;
import edu.uoc.geopocket.common.EnableCommonModule;
import edu.uoc.geopocket.liquec.EnableLiquecModule;
import edu.uoc.geopocket.project.EnableProjectModule;
import edu.uoc.geopocket.statistics.EnableStatisticsModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@EnableCommonModule
@EnableProjectModule
@EnableBerockModule
@EnableLiquecModule
@EnableStatisticsModule
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class GeoPocket {

	public static void main(String[] args) {
		SpringApplication.run(GeoPocket.class, args);
	}

}

