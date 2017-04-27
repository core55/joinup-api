package io.github.core55.authentication;

import io.github.core55.email.EmailService;
import io.github.core55.meetup.Meetup;
import io.github.core55.user.User;
import io.github.core55.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

/**
 * Created by S. Stefani on 2017-04-27.
 */
@RestController
@RequestMapping("api/login")
public class LoginController {

    private final JavaMailSender javaMailSender;

    @Autowired
    public LoginController(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public String addUserToMap(@RequestBody String username) {
        EmailService emailService = new EmailService(javaMailSender);

        emailService.sendMail(username, "Login in CuLater", "Testing123");

        return "Email sent";
    }
}
