package io.github.core55;

import io.github.core55.user.User;
import io.github.core55.meetup.Meetup;
import io.github.core55.tokens.MD5Util;
import io.github.core55.user.UserRepository;
import io.github.core55.user.UserEventHandler;
import io.github.core55.meetup.MeetupRepository;
import org.springframework.stereotype.Component;
import org.springframework.boot.ApplicationRunner;
import io.github.core55.meetup.MeetupEventHandler;
import io.github.core55.location.LocationRepository;
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

    /**
     * Populate the database with some data for testing purpose.
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Meetup meetup = new Meetup(18.071716, 59.326830, 13);
        meetup.setPinLongitude(18.043155);
        meetup.setPinLatitude(59.328986);
        meetupEventHandler.setMeetupHash(meetup);

        User Phillip = new User(18.072311, 59.316486);
        Phillip.setNickname("Phillip");
        Phillip.setUsername("phillip.example@gmail.com");
        Phillip.setGravatarURI("https://www.gravatar.com/avatar/" + MD5Util.md5Hex("phillip.example@gmail.com"));
        Phillip.setPassword("secret");
        Phillip.setStatus("On my way!");
        meetup.getUsers().add(Phillip);
        Phillip.getMeetups().add(meetup);

        User Simone = new User(18.098452, 59.337490);
        Simone.setNickname("Simone");
        Simone.setUsername("s.stefani95@gmail.com");
        Simone.setGravatarURI("https://www.gravatar.com/avatar/" + MD5Util.md5Hex("s.stefani95@gmail.com"));
        Simone.setPassword("secret");
        Simone.setStatus("Almost done!");
        meetup.getUsers().add(Simone);
        Simone.getMeetups().add(meetup);

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
        users.save(Simone);
        users.save(Marcel);
        users.save(Jiho);
    }
}
