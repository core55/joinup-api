package io.core55.drycherry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/**
 * Created by cedricseger on 2017-04-21.
 */

@RestController
@RequestMapping("/users")
public class UserController {

    /**
     * Automatically injects the UserRepository bean
     */
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public User create(@RequestBody User user) {
        return userRepository.save(user);
    }

}