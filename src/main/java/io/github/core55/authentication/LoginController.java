package io.github.core55.authentication;

import io.github.core55.core.StringResponse;
import io.github.core55.email.EmailService;
import io.github.core55.email.MailContentBuilder;
import io.github.core55.meetup.Meetup;
import io.github.core55.user.User;
import io.github.core55.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Created by S. Stefani on 2017-04-27.
 */
@RestController
@RequestMapping("api/login")
public class LoginController {

    private final JavaMailSender javaMailSender;
    private final MailContentBuilder mailContentBuilder;

    @Autowired
    public LoginController(JavaMailSender javaMailSender, MailContentBuilder mailContentBuilder) {
        this.javaMailSender = javaMailSender;
        this.mailContentBuilder = mailContentBuilder;
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public StringResponse addUserToMap(@RequestBody User user) {

        EmailService emailService = new EmailService(javaMailSender, mailContentBuilder);

        emailService.prepareAndSend(user.getUsername(), "Login in CuLater", generateLink());

        return new StringResponse("Email sent");
    }

    private String generateLink() {
        String initial = UUID.randomUUID().toString().replaceAll("-", "");
        String end = UUID.randomUUID().toString().replaceAll("-", "");

        return "/api/login/" + initial + end;
    }
}
