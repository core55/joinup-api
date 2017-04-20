package io.core55.drycherry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by S. Stefani on 2017-04-20.
 */

@RestController
@RequestMapping("/meetups")
public class MeetupController {

    @Autowired
    private MeetupRepository meetupRepository;

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public Meetup create(@RequestBody Meetup meetup) {

        return meetupRepository.save(meetup);
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Iterable<Meetup> index() {

        return meetupRepository.findAll();
    }
}
