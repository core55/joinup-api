package io.github.core55.config;

import io.github.core55.user.User;
import io.github.core55.user.UserRepository;
import org.springframework.http.HttpMethod;
import io.github.core55.user.DetailsService;
import io.github.core55.authentication.JWTLoginFilter;
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

    @Autowired
    private UserRepository userRepository;

    /**
     * When Spring Security is enabled it is important to define which routes can be accessed and which authentication
     * procedures needs to be applied. This API uses two highly customised authentication approaches (magic link and
     * Google sign-in) which both return a User entity and a JWT. The following configure method allows access to the
     * routes /api/login/** and requires the user to be authenticated and bearing a JWT to access any other route.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/login").permitAll()
                .antMatchers(HttpMethod.GET, "/api/meetups").access("hasRole('ROLE_ADMIN')")
                .antMatchers(HttpMethod.POST, "/api/meetups").permitAll()
                .antMatchers(HttpMethod.GET, "/api/meetups/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/meetups/**").access("hasRole('ROLE_ASSOCIATEDUSERS')")
                .antMatchers(HttpMethod.PATCH, "/api/meetups/**").access("hasRole('ROLE_ASSOCIATEDUSERS')")
                .antMatchers(HttpMethod.DELETE, "/api/meetups/**").access("hasRole('ROLE_CREATOR')")
                .antMatchers(HttpMethod.POST, "api/meetups/**/users/save").permitAll()
                .antMatchers(HttpMethod.GET, "api/meetups/**/users").access("hasRole('ROLE_ASSOCIATEDUSERS')")
                .antMatchers(HttpMethod.GET, "api/users").access("hasRole('ROLE_ADMIN')")
                .antMatchers(HttpMethod.POST, "api/users").access("hasRole('ROLE_ADMIN')")
                .antMatchers(HttpMethod.GET, "api/users/**").access("hasRole('ROLE_OWNUSER')")
                .antMatchers(HttpMethod.PUT, "api/users/**").access("hasRole('ROLE_OWNUSER')")
                .antMatchers(HttpMethod.PATCH, "api/users/**").access("hasRole('ROLE_OWNUSER')")
                .antMatchers(HttpMethod.DELETE, "api/users/**").access("hasRole('ROLE_OWNUSER')")
                .antMatchers(HttpMethod.GET, "api/users/**/locations").access("hasRole('ROLE_OWNUSER')")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JWTLoginFilter("/api/login", authenticationManager(), userRepository),
                        UsernamePasswordAuthenticationFilter.class)
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

//@Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable().authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/api/login").permitAll()
//                .antMatchers("/api/login/**").permitAll()
//                .antMatchers("/api/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .addFilterBefore(new JWTLoginFilter("/api/login", authenticationManager(), userRepository),
//                        UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(new JWTAuthenticationFilter(),
//                        UsernamePasswordAuthenticationFilter.class);
//    }