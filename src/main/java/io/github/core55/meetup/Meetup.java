/**
 * Meetup.java
 *
 * Created by S. Stefani on 2017-04-20.
 * Edited by P. Gajland on 2017-04-21.
 */

package io.github.core55.meetup;

import javax.persistence.*;
import io.github.core55.core.BaseEntity;

@Entity
//@NamedQuery(name = "Meetup.findByHash",
//        query = "select m from Meetup m where m.hash = ?1")
//@Table(name = "meetup")
public class Meetup extends BaseEntity {

    private Double centerLongitude;
    private Double centerLatitude;

    private Integer zoomLevel;

    private String hash;

    private Double pinLongitude;
    private Double pinLatitude;

    private String name;

    private String createdTime;
    private String updatedTime;

    protected Meetup() {
        super();
    }

//    @ElementCollection(targetClass = User.class)
//    private Set<User> users;
//
//    @ManyToMany(mappedBy = "user")
//    public Set<User> getUsers() {
//        return users;
//    }
//
//    public void setUsers(Set<User> users) {
//        this.users = users;
//    }


//    @PrePersist
//    public void prePersist() {
//        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
//        this.createdTime = timeStamp;
//        this.updatedTime = timeStamp;
//    }
//
//    @PreUpdate
//    public void preUpdate() {
//        this.updatedTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
//    }
}