package io.github.core55.authentication;

import io.github.core55.core.StringResponse;
import io.github.core55.email.EmailService;
import io.github.core55.email.MailContentBuilder;
import io.github.core55.user.User;
import io.github.core55.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
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

    @Autowired
    public LoginController(JavaMailSender javaMailSender, MailContentBuilder mailContentBuilder, UserRepository userRepository) {
        this.javaMailSender = javaMailSender;
        this.mailContentBuilder = mailContentBuilder;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public StringResponse sendLoginEmail(@RequestBody User user) {

        String token = generateLink();
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
            TokenAuthenticationService.addAuthentication(res, user.getUsername());
            return new StringResponse("User authenticated successfully!");
        } else {
            return new StringResponse("Couldn't authenticate the user! " + token);
        }
    }

    private String generateLink() {
        String initial = UUID.randomUUID().toString().replaceAll("-", "");
        String end = UUID.randomUUID().toString().replaceAll("-", "");
        String time = new Date().toString();
        String encTime = "";
        try {
            encTime = AESenc.encrypt(time);
        } catch (Exception error) {
            System.out.println("Error: AES failed");
        }
        return initial + end + encTime;
    }
}
