package io.github.core55.meetup;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * Created by S. Stefani on 2017-04-27.
 */
public class MeetupService {

    @Autowired
    private final MeetupRepository meetupRepository;

    public MeetupService(MeetupRepository meetupRepository) {
        this.meetupRepository = meetupRepository;
    }

    public String generateHash() {
        int count = 0;
        boolean flag = false;
        String hash;

        while (!flag && count < 10) {
            hash = UUID.randomUUID().toString().replaceAll("-", "");
            Meetup meetup = meetupRepository.findByHash(hash);
            if (meetup == null) {
                flag = true;
                return hash;
            }
            count++;
        }

        return null;
    }
}
