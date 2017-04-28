package io.github.core55.config;

import org.springframework.context.annotation.Configuration;
import io.github.core55.authentication.JWTAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * When Spring Security is enabled it is important to define which routes can be accessed and which authentication
     * procedures needs to be applied. This API uses two highly customised authentication approaches (magic link and
     * Google sign-in) which both return a User entity and a JWT. The following configure method allows access to the
     * routes /api/login/** and requires the user to be authenticated and bearing a JWT to access any other route.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/api/login/**").permitAll()
                .antMatchers("/api/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
