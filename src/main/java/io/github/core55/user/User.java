/**
 * User.java
 *
 * Created by C. Seger on 2017-04-21.
 */

package io.github.core55.user;

import javax.persistence.*;
import io.github.core55.core.BaseEntity;

@Entity
//@Table(name = "user")
public class User extends BaseEntity {

//    @ElementCollection(targetClass = Meetup.class)
//    private Set<Meetup> meetups;

    private String nickname;

    private Double lastLongitude;
    private Double lastLatitude;

//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "users_meetups", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "meetup_id", referencedColumnName = "id"))
//    public Set<Meetup> getMeetups(){
//        return this.meetups;
//    }

    protected User() {
        super();
    }
}
