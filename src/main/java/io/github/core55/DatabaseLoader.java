/**
 * DatabaseLoader.java
 *
 * Created by S. Stefani on 2017-04-22.
 * Edited by P. Gajland on 2017-04-24.
 */

package io.github.core55;

import io.github.core55.location.LocationRepository;
import io.github.core55.meetup.MeetupEventHandler;
import io.github.core55.user.User;
import io.github.core55.meetup.Meetup;
import io.github.core55.user.UserEventHandler;
import io.github.core55.user.UserRepository;
import io.github.core55.meetup.MeetupRepository;
import org.springframework.stereotype.Component;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.ApplicationArguments;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class DatabaseLoader implements ApplicationRunner {

    private final MeetupRepository meetupRepository;
    private final UserRepository users;
    private final LocationRepository locationRepository;
    MeetupEventHandler meetupEventHandler;
    UserEventHandler userEventHandler;

    @Autowired
    public DatabaseLoader(MeetupRepository meetupRepository, UserRepository users, LocationRepository locationRepository) {
        this.meetupRepository = meetupRepository;
        this.locationRepository = locationRepository;
        this.users = users;
        this.meetupEventHandler = new MeetupEventHandler(meetupRepository);
        this.userEventHandler = new UserEventHandler(users, locationRepository);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Meetup meetup = new Meetup(18.071716, 59.326830, 13);
        meetupEventHandler.setMeetupHash(meetup);

        User Phillip = new User(18.072311, 59.316486);
        Phillip.setNickname("Phillip");
        Phillip.setUsername("phillip@gmail.com");
        meetup.getUsers().add(Phillip);
        Phillip.getMeetups().add(meetup);

        User Dean = new User(18.098452, 59.337490);
        Dean.setNickname("Dean");
        userEventHandler.setUserHash(Dean);
        meetup.getUsers().add(Dean);
        Dean.getMeetups().add(meetup);

        User Marcel = new User(18.073754, 59.347299);
        Marcel.setNickname("Marcel");
        userEventHandler.setUserHash(Marcel);
        meetup.getUsers().add(Marcel);
        Marcel.getMeetups().add(meetup);

        User Jiho = new User(18.095502, 59.323243);
        Jiho.setNickname("Jiho");
        userEventHandler.setUserHash(Jiho);
        meetup.getUsers().add(Jiho);
        Jiho.getMeetups().add(meetup);

        meetupRepository.save(meetup);
        users.save(Phillip);
        users.save(Dean);
        users.save(Marcel);
        users.save(Jiho);
    }
}
