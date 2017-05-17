/*
  Authors: S. Stefani, P. Gajland
 */

package io.github.core55;

import org.springframework.boot.SpringApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableScheduling
@SpringBootApplication
public class Application {

    /**
     * Start the Spring Boot application.
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}