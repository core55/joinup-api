package io.github.core55.authentication;

import java.util.Date;
import java.util.UUID;
import java.io.IOException;
import java.util.Collections;
import io.github.core55.user.User;
import io.github.core55.email.EmailService;
import io.github.core55.core.StringResponse;
import io.github.core55.user.UserRepository;
import java.security.GeneralSecurityException;
import com.google.api.client.json.JsonFactory;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import io.github.core55.email.MailContentBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.mail.javamail.JavaMailSender;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import org.springframework.beans.factory.annotation.Autowired;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;

@RestController
@RequestMapping("api/login")
public class LoginController {

    private final JavaMailSender javaMailSender;
    private final MailContentBuilder mailContentBuilder;
    private final UserRepository userRepository;
    private static final JsonFactory jsonFactory = new JacksonFactory();
    private static final NetHttpTransport netHttpTransport = new NetHttpTransport();

    @Autowired
    public LoginController(JavaMailSender javaMailSender, MailContentBuilder mailContentBuilder, UserRepository userRepository) {
        this.javaMailSender = javaMailSender;
        this.mailContentBuilder = mailContentBuilder;
        this.userRepository = userRepository;
    }

    /**
     * Send an email with a magic link for authentication. The user provides an email and a random token is generated
     * and attached to the specific User entity. An email is then sent to the provided address with a special
     * authentication link containing the same token.
     */
    @RequestMapping(value = "/send", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public StringResponse sendLoginEmail(@RequestBody User user) throws EntityNotFoundException {

        String token = generateToken();
        User retrievedUser = userRepository.findByUsername(user.getUsername());

        if (retrievedUser == null) {
            throw new EntityNotFoundException("Couldn't find the user " + user.getUsername());
        }

        retrievedUser.setAuthenticationToken(token);
        userRepository.save(retrievedUser);

        EmailService emailService = new EmailService(javaMailSender, mailContentBuilder);
        emailService.prepareAndSend(user.getUsername(), "Login in CuLater", "/api/login/" + token);

        return new StringResponse("Email sent correctly to " + user.getUsername());
    }

    /**
     * Authenticate a user with a magic link. Extract the token from the magic link and search a User entity with such
     * token. If found generate a JWT and attach it to the response header. Otherwise throw a EntityNotFoundException.
     */
    @RequestMapping(value = "/{token}", method = RequestMethod.GET, produces = "application/json")
    public User authenticateWithMagicLink(@PathVariable("token") String token, HttpServletResponse res)
            throws EntityNotFoundException {

        User user = userRepository.findByAuthenticationToken(token);
        if (user != null) {
            user.setAuthenticationToken(null);
            userRepository.save(user);

            TokenAuthenticationService.addAuthentication(res, user.getUsername());
            return user;
        } else {
            throw new EntityNotFoundException("Couldn't authenticate the user!");
        }
    }

    /**
     * Authenticate a user with Google sign-in token. It expects a Google IdToken from the front-end. It then verifies
     * the validity of the token and retrieve the user data from Google. Generate a JWT and attach it to the response
     * header.
     */
    @RequestMapping(value = "/token", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public User authenticateWithGoogleToken(@RequestBody GoogleToken googleToken, HttpServletResponse res)
            throws GeneralSecurityException, IOException, EntityNotFoundException{

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(netHttpTransport, jsonFactory)
                .setAudience(Collections.singletonList("CLIENT_ID"))
                .build();

        GoogleIdToken idToken = verifier.verify(googleToken.getIdTokenString());
        if (idToken != null) {
            Payload payload = idToken.getPayload();
            String username = payload.getEmail();

            TokenAuthenticationService.addAuthentication(res, username);
            return userRepository.findByUsername(username);
        } else {
            throw new EntityNotFoundException("Couldn't authenticate the user!");
        }

    }

    /**
     * Generate an authentication token which is composed by 64 random characters and the encrypted creation timestamp.
     *
     * @return the newly generated token
     */
    private String generateToken() {
        String initial = UUID.randomUUID().toString().replaceAll("-", "");
        String end = UUID.randomUUID().toString().replaceAll("-", "");

        String time = new Date().toString();
        String encTime = "";
        try {
            encTime = AESenc.encrypt(time);
        } catch (Exception error) {
            System.out.println("Error: AES failed");
        }
        return initial + end + "." + encTime;
    }
}
