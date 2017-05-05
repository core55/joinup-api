package io.github.core55.authentication;

import java.util.UUID;
import java.util.Collections;
import io.github.core55.user.User;
import io.github.core55.tokens.MD5Util;
import io.github.core55.tokens.AuthToken;
import io.github.core55.email.EmailService;
import io.github.core55.core.StringResponse;
import io.github.core55.user.UserRepository;
import org.springframework.hateoas.Resource;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import io.github.core55.email.MailContentBuilder;
import javax.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.*;
import io.github.core55.tokens.AuthTokenRepository;
import org.springframework.security.core.Authentication;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@RestController
@RequestMapping("api/register")
public class RegisterController {

    private final UserRepository userRepository;
    private final AuthTokenRepository authTokenRepository;
    private final JavaMailSender javaMailSender;
    private final MailContentBuilder mailContentBuilder;

    @Autowired
    public RegisterController(UserRepository userRepository, AuthTokenRepository authTokenRepository, JavaMailSender javaMailSender, MailContentBuilder mailContentBuilder) {
        this.userRepository = userRepository;
        this.authTokenRepository = authTokenRepository;
        this.javaMailSender = javaMailSender;
        this.mailContentBuilder = mailContentBuilder;
    }

    /**
     * Register a user to the service. Accepts a payload of username, password and and optional oldUsername. If the
     * oldUsername is provided the API will try to add the new credentials to referenced user. Otherwise it will create
     * a new user entity. It returns the registered user and automatically authenticates it.
     */
    @RequestMapping(value = "/send", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public StringResponse sendRegisterEmail(@RequestBody AccountCredentials creds, HttpServletResponse res) throws EntityNotFoundException {

        String tokenValue = generateToken();
        User retrievedUserByUsername = userRepository.findByUsername(creds.getUsername());

        if (retrievedUserByUsername != null) {
            throw new EntityNotFoundException("The provided email is already taken!");
        }

        User user;
        if (creds.getOldUsername() == null) {
            AuthToken authToken = new AuthToken(tokenValue, creds.getUsername(), creds.getPassword());
            authTokenRepository.save(authToken);
        } else {
            AuthToken authToken = new AuthToken(tokenValue, creds.getUsername(), creds.getPassword());
            user = userRepository.findByUsername(creds.getOldUsername());
            authToken.setUserId(user.getId());
            authTokenRepository.save(authToken);
        }

        EmailService emailService = new EmailService(javaMailSender, mailContentBuilder);
        emailService.prepareAndSend(creds.getUsername(), "Register to Joinup", "/api/register/" + tokenValue);

        return new StringResponse("Email sent correctly to " + creds.getUsername());
    }

    /**
     * Logic to handle email confirmation.
     */
    @RequestMapping(value = "/{token}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<?> emailConfirmation(@PathVariable("token") String token, HttpServletResponse res)
            throws EntityNotFoundException {

        AuthToken authToken = authTokenRepository.findByValue(token);
        if (authToken == null) {
            throw new EntityNotFoundException("The registration link is not valid!");
        }

        User user;
        if (authToken.getUserId() == null) {
            String gravatarURI = "https://www.gravatar.com/avatar/" + MD5Util.md5Hex(authToken.getUsername());
            user = new User(authToken.getUsername(), gravatarURI);
            user.setPlainPassword(authToken.getPassword());
        } else {
            user = userRepository.findOne(authToken.getUserId());
            String gravatarURI = "https://www.gravatar.com/avatar/" + MD5Util.md5Hex(authToken.getUsername());
            user.setUsername(authToken.getUsername());
            user.setPlainPassword(authToken.getPassword());
            user.setGravatarURI(gravatarURI);
        }

        authTokenRepository.delete(authToken);
        userRepository.save(user);

        Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(auth);

        TokenAuthenticationService.addAuthentication(res, user.getUsername());
        Resource<User> resource = new Resource<>(user);
        return ResponseEntity.ok(resource);
    }

    /**
     * Generate an authentication token which is composed by 64 random characters.
     *
     * @return the newly generated token
     */
    private String generateToken() {
        String initial = UUID.randomUUID().toString().replaceAll("-", "");
        String end = UUID.randomUUID().toString().replaceAll("-", "");

        return initial + end;
    }
}
