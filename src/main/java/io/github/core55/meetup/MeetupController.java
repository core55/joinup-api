/**
 * Meetup.java
 *
 * Created by S. Stefani on 2017-04-20.
 * Edited by P. Gajland on 2017-04-21.
 */

package io.github.core55.meetup;

import io.github.core55.user.User;
import org.springframework.hateoas.Resource;
import io.github.core55.user.UserRepository;
import io.github.core55.user.UserEventHandler;
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

    @RequestMapping(value = "/{hash}/users/save", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> addUserToMap(@RequestBody User user, @PathVariable("hash") String hash) {

        setUserHash(user);
        user.setUpdatedAt();
        user.setCreatedAt();

        Meetup meetup = meetupRepository.findByHash(hash);
        user.getMeetups().add(meetup);
        meetup.getUsers().add(user);

        userRepository.save(user);
        meetupRepository.save(meetup);

        Resource<User> resource = new Resource<>(user);

        return ResponseEntity.ok(resource);
    }

    private void setUserHash(User user) {
        UserEventHandler handler = new UserEventHandler(userRepository);
        handler.setUserHash(user);
    }
}