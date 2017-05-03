//package io.github.core55.authentication;
//
//import java.util.Collections;
//import java.util.UUID;
//
//import io.github.core55.core.StringResponse;
//import io.github.core55.email.EmailService;
//import io.github.core55.tokens.MagicLinkToken;
//import io.github.core55.user.User;
//import io.github.core55.user.UserRepository;
//import org.springframework.hateoas.Resource;
//import javax.servlet.http.HttpServletResponse;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import javax.persistence.EntityNotFoundException;
//import org.springframework.security.core.Authentication;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//
//@RestController
//@RequestMapping("api/register")
//public class RegisterController {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
//    public @ResponseBody
//    ResponseEntity<?> registerUser(@RequestBody AccountCredentials creds, HttpServletResponse res) throws EntityNotFoundException {
//
//        User user;
//
//        if (creds.getOldUsername() != null) {
//            user = userRepository.findByUsername(creds.getOldUsername());
//            if (user == null) {
//                throw new EntityNotFoundException("Couldn't find the user " + creds.getOldUsername());
//            }
//        } else {
//            user = new User(creds.getUsername(), creds.getPassword());
//        }
//
//        userRepository.save(user);
//
//        Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername(), null, Collections.emptyList());
//        SecurityContextHolder.getContext().setAuthentication(auth);
//
//        TokenAuthenticationService.addAuthentication(res, user.getUsername());
//        Resource<User> resource = new Resource<>(user);
//        return ResponseEntity.ok(resource);
//    }
//
//    @RequestMapping(value = "/send", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
//    public StringResponse sendRegisterEmail(@RequestBody AccountCredentials creds) throws EntityNotFoundException {
//
//        String tokenValue = generateToken();
//        User retrievedUser = userRepository.findByUsername(creds.getUsername());
//
//        if (retrievedUser == null) {
//            throw new EntityNotFoundException("Couldn't find the user " + creds.getUsername());
//        }
//
//        MagicLinkToken magicLinkToken = new MagicLinkToken(tokenValue, retrievedUser.getId());
//        magicLinkTokenRepository.save(magicLinkToken);
//
//        EmailService emailService = new EmailService(javaMailSender, mailContentBuilder);
//        emailService.prepareAndSend(user.getUsername(), "Login in CuLater", "/api/login/" + tokenValue);
//
//        return new StringResponse("Email sent correctly to " + user.getUsername());
//    }
//
//    /**
//     * Generate an authentication token which is composed by 64 random characters.
//     *
//     * @return the newly generated token
//     */
//    private String generateToken() {
//        String initial = UUID.randomUUID().toString().replaceAll("-", "");
//        String end = UUID.randomUUID().toString().replaceAll("-", "");
//
//        return initial + end;
//    }
//
//}
