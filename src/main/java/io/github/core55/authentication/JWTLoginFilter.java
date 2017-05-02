package io.github.core55.authentication;

import java.io.IOException;
import java.util.Collections;
import com.google.gson.Gson;
import javax.servlet.FilterChain;
import io.github.core55.user.User;
import javax.servlet.ServletException;
import io.github.core55.user.SimpleUser;
import io.github.core55.user.UserRepository;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;


public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    private UserRepository userRepository;
    private Gson gson = new Gson();

    public JWTLoginFilter(String url, AuthenticationManager authManager, UserRepository userRepository) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
        this.userRepository = userRepository;
    }

    /**
     * Attempt authentication with username and password. Retrieves the user's credentials from the incoming HTTP
     * request and attempts to authenticate the user against the database. If successful call successfulAuthentication
     * methods. If it fails send an Unauthorized message with HTTP code 401.
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException, IOException, ServletException {
        AccountCredentials cred = new ObjectMapper().readValue(req.getInputStream(), AccountCredentials.class);

        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(cred.getUsername(), cred.getPassword(), Collections.emptyList())
        );
    }

    /**
     * If the authentication is successful attach a JWT to the header of the HTTP response.
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth)
            throws IOException, ServletException {
        TokenAuthenticationService.addAuthentication(res, auth.getName());

        User user = userRepository.findByUsername(auth.getName());
        SimpleUser simpleUser = new SimpleUser(
                user.getId(),
                user.getNickname(),
                user.getLastLongitude(),
                user.getLastLatitude(),
                user.getUsername(),
                user.getCreatedAt(),
                user.getUpdatedAt());

        String simpleUserJson = gson.toJson(simpleUser);
        res.getWriter().write(simpleUserJson);
    }
}
