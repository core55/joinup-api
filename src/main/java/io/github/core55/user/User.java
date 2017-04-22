/**
 * User.java
 *
 * Created by C. Seger on 2017-04-21.
 */

package io.github.core55.user;

import javax.persistence.*;
import io.github.core55.meetup.Meetup;
import io.github.core55.core.BaseEntity;

@Entity
public class User extends BaseEntity {

    private String nickname;

    private Double lastLongitude;
    private Double lastLatitude;

    @ManyToOne
    private Meetup meetup;

    protected User() {
        super();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Double getLastLongitude() {
        return lastLongitude;
    }

    public void setLastLongitude(Double lastLongitude) {
        this.lastLongitude = lastLongitude;
    }

    public Double getLastLatitude() {
        return lastLatitude;
    }

    public void setLastLatitude(Double lastLatitude) {
        this.lastLatitude = lastLatitude;
    }

    public Meetup getMeetup() {
        return meetup;
    }

    public void setMeetup(Meetup meetup) {
        this.meetup = meetup;
    }
}
