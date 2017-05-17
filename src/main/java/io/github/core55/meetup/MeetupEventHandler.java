/*
  Authors: S. Stefani
 */

package io.github.core55.meetup;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

@Component
@RepositoryEventHandler(Meetup.class)
public class MeetupEventHandler {

    private final MeetupRepository meetupRepository;

    @Autowired
    public MeetupEventHandler(MeetupRepository meetupRepository) {
        this.meetupRepository = meetupRepository;
    }

    /**
     * Set createdAt and updatedAt fields when a Meetup entity is created.
     */
    @HandleBeforeCreate
    public void setMeetupTimestampsOnCreate(Meetup meetup) {
        meetup.setCreatedAt();
        meetup.setUpdatedAt();
    }

    /**
     * Set updatedAt field when a Meetup entity is updated.
     */
    @HandleBeforeSave
    public void setMeetupTimestampOnUpdate(Meetup meetup) {
        meetup.setUpdatedAt();
    }

    /**
     * Set hash field when a Meetup entity is created.
     */
    @HandleBeforeCreate
    public void setMeetupHash(Meetup meetup) {
        MeetupService meetupService = new MeetupService(meetupRepository);
        meetup.setHash(meetupService.generateHash());
    }
}
