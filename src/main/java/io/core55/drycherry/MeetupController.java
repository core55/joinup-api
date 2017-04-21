/**
 * MeetupController.java
 *
 * Created by S. Stefani on 2017-04-20.
 */

package io.core55.drycherry;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/meetups")
public class MeetupController {

    /**
     * Automatically injects the MeetupRepository bean
     */
    @Autowired
    private MeetupRepository meetupRepository;

    /**
     * Persist a meetup entity in the database
     *
     * @param meetup is the meetup object in JSON format
     * @return return the newly created object
     */
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public Meetup create(@RequestBody Meetup meetup) {

        return meetupRepository.save(meetup);
    }

    /**
     * List all the meetups in the database
     *
     * @return a list of meetups
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Iterable<Meetup> index() {

        return meetupRepository.findAll();
    }
}
