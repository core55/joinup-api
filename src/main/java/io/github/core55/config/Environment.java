/*
  Authors: S. Stefani
 */

package io.github.core55.config;

import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Component
@EnableConfigurationProperties
@ConfigurationProperties("joinup.env")
public class Environment {

    private String googleauth = "joinup";

    public String getGoogleauth() {
        return googleauth;
    }

    public void setGoogleauth(String googleauth) {
        this.googleauth = googleauth;
    }
}
