package io.core55.drycherry;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by cedricseger on 2017-04-21.
 */


@Entity
@Table(name = "user")
public class User {

    @ElementCollection(targetClass = Meetup.class)
    private Set<Meetup> meetups;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(length = 20)
    private String name;

    @Column(name = "last_longitude")
    private Double lastLongitude;

    @Column(name = "last_latitude")
    private Double lastLatitude;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "users_meetups", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "meetup_id", referencedColumnName = "id"))
    public Set<Meetup> getMeetups(){
        return this.meetups;
    }

    public void setMeetups(Set<Meetup> meetups){
        this.meetups = meetups;
    }

    public Long getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
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

}
