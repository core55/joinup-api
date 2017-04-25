/**
 * DatabaseLoader.java
 *
 * Created by S. Stefani on 2017-04-22.
 * Edited by P. Gajland on 2017-04-24.
 */

package io.github.core55.core;

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
    MeetupEventHandler meetupEventHandler;
    UserEventHandler userEventHandler;

    @Autowired
    public DatabaseLoader(MeetupRepository meetupRepository, UserRepository users) {
        this.meetupRepository = meetupRepository;
        this.users = users;
        this.meetupEventHandler = new MeetupEventHandler(meetupRepository);
        this.userEventHandler = new UserEventHandler(users);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Meetup meetup = new Meetup(18.071716, 59.326830, 13);
        meetupEventHandler.setMeetupHash(meetup);
        meetupEventHandler.setMeetupTimestampsOnCreate(meetup);

        User Phillip = new User(18.072311, 59.316486);
        Phillip.setNickname("Phillip");
        userEventHandler.setUserHash(Phillip);
        userEventHandler.setUserTimestampsOnCreate(Phillip);
        meetup.getUsers().add(Phillip);
        Phillip.getMeetups().add(meetup);

        User Dean = new User(18.098452, 59.337490);
        Dean.setNickname("Dean");
        userEventHandler.setUserHash(Dean);
        userEventHandler.setUserTimestampsOnCreate(Dean);
        meetup.getUsers().add(Dean);
        Dean.getMeetups().add(meetup);

        User Marcel = new User(18.073754, 59.347299);
        Marcel.setNickname("Marcel");
        userEventHandler.setUserHash(Marcel);
        userEventHandler.setUserTimestampsOnCreate(Marcel);
        meetup.getUsers().add(Marcel);
        Marcel.getMeetups().add(meetup);

        User Jiho = new User(18.095502, 59.323243);
        Jiho.setNickname("Jiho");
        userEventHandler.setUserHash(Jiho);
        userEventHandler.setUserTimestampsOnCreate(Jiho);
        meetup.getUsers().add(Jiho);
        Jiho.getMeetups().add(meetup);

        meetupRepository.save(meetup);
        users.save(Phillip);
        users.save(Dean);
        users.save(Marcel);
        users.save(Jiho);
    }
}
