/**
 * Meetup.java
 *
 * Created by S. Stefani on 2017-04-20.
 * Edited by P. Gajland on 2017-04-21.
 */

package io.github.core55.meetup;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;


@RepositoryRestController
public class MeetupController {

    private final MeetupRepository meetupRepository;

    @Autowired
    public MeetupController(MeetupRepository meetupRepository) {
        this.meetupRepository = meetupRepository;
    }


//    /**
//     * Linking a user to a meetup
//     */
//    @RequestMapping(value = "/{hash}/users", method = RequestMethod.POST, produces = "application/json")
//    public User addUser(@RequestBody User newUser, @PathVariable("hash") String hash) {
//
//        Meetup meetup = meetupRepository.findByHash(hash);
//
//        if(newUser.getId() != null){
//            User currentUser = userRepository.findOne(newUser.getId());
//            currentUser.setLastLongitude(newUser.getLastLongitude());
//            currentUser.setLastLatitude(newUser.getLastLatitude());
//            return userRepository.save(currentUser);
//        } else {
//            return userRepository.save(newUser);
//        }
//    }
}