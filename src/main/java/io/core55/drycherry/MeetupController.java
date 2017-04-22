///**
// * Meetup.java
// * <p>
// * Created by S. Stefani on 2017-04-20.
// * Edited by P. Gajland on 2017-04-21.
// */
//
//package io.core55.drycherry;
//
//import io.github.core55.meetup.Meetup;
//import io.github.core55.meetup.MeetupRepository;
//import io.github.core55.user.User;
//import io.github.core55.user.UserRepository;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import javax.transaction.Transactional;
//import java.io.IOException;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/meetups")
//public class MeetupController {
//
//    /**
//     * Automatically injects the MeetupRepository bean
//     */
//    @Autowired
//    private MeetupRepository meetupRepository;
//    private UserRepository userRepository;
//
//    /**
//     * Persist a meetup entity in the database
//     *
//     * @param meetup is the meetup object in JSON format
//     * @return return the newly created object
//     */
//    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
//    public Meetup create(@RequestBody Meetup meetup) {
//
//        String hash = generateHash();
//        if (hash == null) throw new RuntimeException("Generating unique hash failed");
//        meetup.setHash(hash);
//        return meetupRepository.save(meetup);
//    }
//
//    /**
//     * List all the meetups in the database
//     *
//     * @return a list of meetups
//     */
//    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
//    public @ResponseBody
//    Iterable<Meetup> index() {
//
//        return meetupRepository.findAll();
//    }
//
//    /**
//     * Update an existing meetup in the database
//     *
//     * @param newMeetup is the meetup object in JSON format
//     * @return return the updated object
//     */
//    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
//    @Transactional
//    public Meetup update(@RequestBody Meetup newMeetup, @PathVariable("id") long id) throws IOException {
//
//        Meetup updatedMeetup = meetupRepository.findOne(id);
//        updatedMeetup.setCenterLatitude(newMeetup.getCenterLatitude());
//        updatedMeetup.setCenterLongitude(newMeetup.getCenterLongitude());
//
//        return meetupRepository.save(updatedMeetup);
//    }
//
//    /**
//     * Delete an existing meetup in the database
//     */
//    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
//    public void delete(@PathVariable("id") long id) {
//        meetupRepository.delete(id);
//    }
//
//    /**
//     * Show an existing meetup from the database given its id
//     */
//    @RequestMapping(value = "/{hash}", method = RequestMethod.GET, produces = "application/json")
//    public Meetup show(@PathVariable("hash") String hash) {
//
//        return meetupRepository.findByHash(hash);
//    }
//
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
//
//
//
//    public int count = 0;
//
//    private String generateHash() {
//
//        boolean flag = false;
//        String hash;
//        while (!flag && count < 10) {
//            hash = UUID.randomUUID().toString().replaceAll("-", "");
//            Meetup meetup = meetupRepository.findByHash(hash);
//            if (meetup == null) {
//                flag = true;
//                return hash;
//            }
//            count++;
//        }
//        return null;
//    }
//}