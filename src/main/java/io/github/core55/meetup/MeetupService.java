/*
  Authors: S. Stefani, P. Gajland
 */

package io.github.core55.meetup;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

public class MeetupService {

    @Autowired
    private final MeetupRepository meetupRepository;

    public MeetupService(MeetupRepository meetupRepository) {
        this.meetupRepository = meetupRepository;
    }

    /**
     * Generate a unique hash for a Meetup entity.
     *
     * @return the newly created hash.
     */
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
