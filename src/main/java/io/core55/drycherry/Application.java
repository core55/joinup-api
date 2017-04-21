/**
 * Application.java
 *
 * Created by S. Stefani on 2017-04-20.
 */

package io.core55.drycherry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    /**
     * Entry point for Spring Boot web app.
     * Allows to bootstrap, compile and run a web app starting from simple models and controllers.
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}