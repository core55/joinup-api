package io.github.core55.authentication;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import io.github.core55.core.StringResponse;
import io.github.core55.email.EmailService;
import io.github.core55.email.MailContentBuilder;
import io.github.core55.user.User;
import io.github.core55.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.UUID;

/**
 * Created by S. Stefani on 2017-04-27.
 */
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

    @RequestMapping(value = "/send", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public StringResponse sendLoginEmail(@RequestBody User user) {

        String token = generateToken();
        User retrievedUser = userRepository.findByUsername(user.getUsername());

        if (retrievedUser == null) {
            return new StringResponse("Couldn't find the user " + user.getUsername());
        }

        retrievedUser.setAuthenticationToken(token);
        userRepository.save(retrievedUser);

        EmailService emailService = new EmailService(javaMailSender, mailContentBuilder);
        emailService.prepareAndSend(user.getUsername(), "Login in CuLater", "/api/login/" + token);

        return new StringResponse("Email sent");
    }

    @RequestMapping(value = "/{token}", method = RequestMethod.GET, produces = "application/json")
    public StringResponse authenticateWithMagicLink(@PathVariable("token") String token, HttpServletResponse res) {

        User user = userRepository.findByAuthenticationToken(token);
        if (user != null) {
            user.setAuthenticationToken(null);
            userRepository.save(user);

            TokenAuthenticationService.addAuthentication(res, user.getUsername());
            return new StringResponse("User " + user.getUsername() + " authenticated successfully!");
        } else {
            return new StringResponse("Couldn't authenticate the user!");
        }
    }

    @RequestMapping(value = "/token", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public StringResponse authenticateWithGoogleToken(@RequestBody GoogleToken googleToken, HttpServletResponse res) throws GeneralSecurityException, IOException {

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(netHttpTransport, jsonFactory)
                .setAudience(Collections.singletonList("CLIENT_ID"))
                .build();

        // (Receive idTokenString by HTTPS POST)
        GoogleIdToken idToken = verifier.verify(googleToken.getIdTokenString());
        if (idToken != null) {
            Payload payload = idToken.getPayload();

            // Get profile information from payload
            String username = payload.getEmail();

            TokenAuthenticationService.addAuthentication(res, username);
            return new StringResponse("User " + username + " authenticated successfully!");
        } else {
            return new StringResponse("Couldn't authenticate the user! ");
        }

    }

    private String generateToken() {
        String initial = UUID.randomUUID().toString().replaceAll("-", "");
        String end = UUID.randomUUID().toString().replaceAll("-", "");

        return initial + end + ".";
    }
}
