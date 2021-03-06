/*
  Authors: S. Stefani, P. Gajland
 */

package io.github.core55.config;

import io.github.core55.user.User;
import io.github.core55.user.DetailsService;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import io.github.core55.authentication.JWTAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DetailsService userDetailsService;

    /**
     * When Spring Security is enabled it is important to define which routes can be accessed and which authentication
     * procedures needs to be applied. This API uses two highly customised authentication approaches (magic link and
     * Google sign-in) which both return a User entity and a JWT. The following configure method allows access to the
     * routes /api/login/** and requires the user to be authenticated and bearing a JWT to access any other route.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/api/**").permitAll() // Temporary
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JWTAuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * Configure which object that contains user-specific data should be used for authentication purpose. Here it is the
     * User DetailService. A encoding strategy for the user password is also provided.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(User.PASSWORD_ENCODER);
    }
}