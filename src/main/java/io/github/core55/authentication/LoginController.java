/*
  Authors: S. Stefani, P. Gajland
 */

package io.github.core55.authentication;

import java.util.UUID;
import java.io.IOException;
import java.util.Collections;

import io.github.core55.email.MailTemplate;
import io.github.core55.response.ErrorUnauthorized;
import io.github.core55.response.ErrorUnprocessableEntity;
import io.github.core55.user.User;
import org.springframework.http.HttpStatus;
import io.github.core55.response.StringResponse;
import io.github.core55.user.UserRepository;
import org.springframework.hateoas.Resource;

import java.security.GeneralSecurityException;

import io.github.core55.tokens.MagicLinkToken;
import com.google.api.client.json.JsonFactory;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import io.github.core55.tokens.MagicLinkTokenRepository;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import org.springframework.beans.factory.annotation.Autowired;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import org.springframework.security.core.context.SecurityContextHolder;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@RestController
@RequestMapping("api/login")
public class LoginController {

    private final UserRepository userRepository;
    private final MagicLinkTokenRepository magicLinkTokenRepository;
    private static final JsonFactory jsonFactory = new JacksonFactory();
    private static final NetHttpTransport netHttpTransport = new NetHttpTransport();

    @Autowired
    public LoginController(UserRepository userRepository, MagicLinkTokenRepository magicLinkTokenRepository) {
        this.userRepository = userRepository;
        this.magicLinkTokenRepository = magicLinkTokenRepository;
    }

    /**
     * Send an email with a magic link for authentication. The user provides an email and a random token is generated
     * and attached to the specific User entity. An email is then sent to the provided address with a special
     * authentication link containing the same token.
     */
    @RequestMapping(value = "/send", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public @ResponseBody ResponseEntity<?> sendLoginEmail(@RequestBody User user) {

        String tokenValue = generateToken();
        User retrievedUser = userRepository.findByUsername(user.getUsername());

        if (retrievedUser == null) {
            return new ResponseEntity<>(new ErrorUnprocessableEntity("Can't find the user " + retrievedUser.getUsername()), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        MagicLinkToken magicLinkToken = new MagicLinkToken(tokenValue, retrievedUser.getId());
        magicLinkTokenRepository.save(magicLinkToken);

        MailTemplate mailTemplate = new MailTemplate();
        mailTemplate.prepareAndSend(user.getUsername(), "Login to Joinup", "login/" + tokenValue, "2bb9182b-6456-4b41-b94b-e298fd3a8895", "Login");

        Resource<StringResponse> resource = new Resource<>(new StringResponse("Email sent correctly to " + user.getUsername()));
        return ResponseEntity.ok(resource);
    }

    /**
     * Authenticate a user with a magic link. Extract the token from the magic link and search a User entity with such
     * token. If found generate a JWT and attach it to the response header. Otherwise throw a EntityNotFoundException.
     */
    @RequestMapping(value = "/{token}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<?> authenticateWithMagicLink(@PathVariable("token") String token, HttpServletResponse res) {

        MagicLinkToken magicLinkToken = magicLinkTokenRepository.findByValue(token);
        if (magicLinkToken == null) {
            return new ResponseEntity<>(new ErrorUnprocessableEntity("This magic link is not valid!"), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        User user = userRepository.findOne(magicLinkToken.getUserId());
        if (user != null) {
            magicLinkTokenRepository.delete(magicLinkToken);

            Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername(), null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(auth);

            TokenAuthenticationService.addAuthentication(res, user.getUsername());
            Resource<User> resource = new Resource<>(user);
            return ResponseEntity.ok(resource);
        } else {
            return new ResponseEntity<>(new ErrorUnauthorized("Couldn't authenticate the user!"), HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Authenticate a user with Google sign-in token. It expects a Google IdToken from the front-end. It then verifies
     * the validity of the token and retrieve the user data from Google. Generate a JWT and attach it to the response
     * header.
     */
    @RequestMapping(value = "/token", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public @ResponseBody ResponseEntity<?> authenticateWithGoogleToken(@RequestBody GoogleToken googleToken, HttpServletResponse res)
            throws GeneralSecurityException, IOException {

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(netHttpTransport, jsonFactory)
                .setAudience(Collections.singletonList("517650150673-424j9n9mmf122j2fqid2vggqupv7n3di.apps.googleusercontent.com"))
                .build();

        GoogleIdToken idToken = verifier.verify(googleToken.getIdToken());
        if (idToken != null) {
            Payload payload = idToken.getPayload();
            String username = payload.getEmail();
            String pictureUrl = (String) payload.get("picture");

            User user = userRepository.findByUsername(username);

            if (user == null) {
                return new ResponseEntity<>(new ErrorUnprocessableEntity("Can't find the user " + username), HttpStatus.UNPROCESSABLE_ENTITY);
            }

            if (user.getGooglePictureURI() == null) {
                user.setGooglePictureURI(pictureUrl);
            }

            userRepository.save(user);

            Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername(), null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(auth);

            TokenAuthenticationService.addAuthentication(res, username);
            Resource<User> resource = new Resource<>(user);
            return ResponseEntity.ok(resource);
        } else {
            return new ResponseEntity<>(new ErrorUnauthorized("Couldn't authenticate the user!"), HttpStatus.UNAUTHORIZED);
        }

    }

    /**
     * Authenticate a user with basic username and password. Use BCrypt password encoder to authorize. Return JWT and
     * authenticated user entity.
     */
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public @ResponseBody ResponseEntity<?> basicAuthentication(@RequestBody AccountCredentials credentials, HttpServletResponse res) {

        User user = userRepository.findByUsername(credentials.getUsername());

        if (user == null) {
            return new ResponseEntity<>(new ErrorUnprocessableEntity("Can't find the user " + credentials.getUsername()), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if (User.PASSWORD_ENCODER.matches(credentials.getPassword(), user.getPassword())) {
            Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(auth);

            TokenAuthenticationService.addAuthentication(res, user.getUsername());
            Resource<User> resource = new Resource<>(user);
            return ResponseEntity.ok(resource);
        } else {
            return new ResponseEntity<>(new ErrorUnauthorized("Your username or password is incorrect"), HttpStatus.UNAUTHORIZED);
        }
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
