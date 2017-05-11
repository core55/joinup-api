package io.github.core55.meetup;

import io.github.core55.response.ErrorUnprocessableEntity;
import io.github.core55.user.User;
import io.github.core55.user.UserService;
import org.springframework.hateoas.Resource;
import io.github.core55.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
@RequestMapping("api/meetups")
public class MeetupController {

    @Autowired
    private MeetupRepository meetupRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Save a new User entity and link it to a specific Meetup.
     */
    @RequestMapping(value = "/{hash}/users/save", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public @ResponseBody ResponseEntity<?> addUserToMap(@RequestBody User user, @PathVariable("hash") String hash) {

        Meetup meetup = meetupRepository.findByHash(hash);

        if (user.getId() != null) {
            User currentUser = userRepository.findOne(user.getId());
            if (currentUser == null) {
                return new ResponseEntity<>(new ErrorUnprocessableEntity("The provided user doesn't exist"), HttpStatus.UNPROCESSABLE_ENTITY);
            }

            currentUser.getMeetups().add(meetup);
            meetup.getUsers().add(currentUser);

            userRepository.save(currentUser);
            meetupRepository.save(meetup);

            Resource<User> resource = new Resource<>(currentUser);

            return ResponseEntity.ok(resource);
        }

        UserService userService = new UserService(userRepository);
        user.setUsername(userService.generateHash());

        user.setUpdatedAt();
        user.setCreatedAt();

        user.getMeetups().add(meetup);
        meetup.getUsers().add(user);

        userRepository.save(user);
        meetupRepository.save(meetup);

        Resource<User> resource = new Resource<>(user);

        return ResponseEntity.ok(resource);
    }
}