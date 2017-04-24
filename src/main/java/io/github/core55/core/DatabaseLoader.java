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
        Meetup meetup = new Meetup(17.950170399999934, 59.404937999999994, 3);
        meetupEventHandler.setMeetupHash(meetup);
        meetupEventHandler.setMeetupTimestampsOnCreate(meetup);

        User user = new User(59.402064, 17.955437);
        user.setNickname("Bob the Builder");
        userEventHandler.setUserHash(user);
        userEventHandler.setUserTimestampsOnCreate(user);

        meetup.getUsers().add(user);
        user.getMeetups().add(meetup);

        meetupRepository.save(meetup);
        users.save(user);
    }
}
