/**
 * MeetupEventHandler.java
 *
 * Created by S. Stefani on 2017-04-22.
 */

package io.github.core55.meetup;

import java.util.Date;
import java.util.UUID;
import java.text.SimpleDateFormat;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

@Component
@RepositoryEventHandler(Meetup.class)
public class MeetupEventHandler {

    private final MeetupRepository meetups;

    @Autowired
    public MeetupEventHandler(MeetupRepository meetups) {
        this.meetups = meetups;
    }

    @HandleBeforeCreate
    public void setMeetupTimestampsOnCreate(Meetup meetup) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        meetup.setCreatedAt(timeStamp);
        meetup.setUpdatedAt(timeStamp);
    }

    @HandleBeforeSave
    public void setMeetupsTimestampOnUpdate(Meetup meetup) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        meetup.setUpdatedAt(timeStamp);
    }

    @HandleBeforeCreate
    public void setMeetupHash(Meetup meetup) {
        meetup.setHash(generateHash());
    }

    private String generateHash() {
        int count = 0;
        boolean flag = false;
        String hash;

        while (!flag && count < 10) {
            hash = UUID.randomUUID().toString().replaceAll("-", "");
            Meetup meetup = meetups.findByHash(hash);
            if (meetup == null) {
                flag = true;
                return hash;
            }
            count++;
        }

        return null;
    }
}
