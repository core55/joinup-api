/**
 * User.java
 *
 * Created by C. Seger on 2017-04-21.
 * Edited by P. Gajland on 2017-04-24.
 */

package io.github.core55.user;

import java.util.List;
import javax.persistence.*;
import java.util.ArrayList;
import io.github.core55.meetup.Meetup;
import io.github.core55.core.BaseEntity;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

@Entity
public class User extends BaseEntity {

    @Size(min = 1, max = 50)
    private String nickname;

    @NotNull
    private Double lastLongitude;
    @NotNull
    private Double lastLatitude;

//    @NotNull
    private String hash;

    private String createdAt;
    private String updatedAt;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @ManyToMany
    @JoinTable(
            name = "meetup_user",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "meetup_id")})
    private List<Meetup> meetups = new ArrayList<>();

    protected User() {
        super();
    }

    public User(Double lastLongitude, Double lastLatitude) {
        this();
        this.lastLongitude = lastLongitude;
        this.lastLatitude = lastLatitude;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Meetup> getMeetups() {
        return meetups;
    }

    public void setMeetups(List<Meetup> meetups) {
        this.meetups = meetups;
    }
}
