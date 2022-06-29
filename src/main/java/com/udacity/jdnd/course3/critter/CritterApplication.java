package com.udacity.jdnd.course3.critter;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Launches the Spring application. Unmodified from starter code.
 */

@EnableWebMvc
@SpringBootApplication
public class CritterApplication {

	public static void main(String[] args) {
		SpringApplication.run(CritterApplication.class, args);
	}
}
