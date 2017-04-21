/**
 * Meetup.java
 *
 * Created by S. Stefani on 2017-04-20.
 */

package io.core55.drycherry;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "meetup")
public class Meetup {

    @ElementCollection(targetClass = User.class)
    private Set<User> users;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "created_at")
    private String createdTime;

    @Column(name = "updated_at")
    private String updatedTime;

    @Column(length = 20)
    private String name;

    @Column(name = "center_longitude", nullable = false)
    private Double centerLongitude;

    @Column(name = "center_latitude", nullable = false)
    private Double centerLatitude;

    @Column(name = "number_of_users")
    private Integer numberOfUsers;

    @Column(name = "zoom_level")
    private Integer zoomLevel;

    @Column(name = "pin_longitude")
    private Double pinLongitude;

    @Column(name = "pin_latitude")
    private Double pinLatitude;

    @ManyToMany(mappedBy = "user")
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }


    @PrePersist
    public void prePersist() {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        this.createdTime = timeStamp;
        this.updatedTime = timeStamp;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    }

    public Meetup() {
        this.numberOfUsers = 1;
    }

    public Long getId() {
        return id;
    }

    public String getCreatedTime(){
        return createdTime;
    }

    public String getUpdatedTime(){
        return updatedTime;
    }

    public String getName(){
        return name;
    }

    public Double getCenterLongitude() {
        return centerLongitude;
    }

    public Double getCenterLatitude() {
        return centerLatitude;
    }

    public void setCenterLongitude(double initialLongitude) {
        this.centerLongitude = initialLongitude;
    }

    public void setCenterLatitude(double initialLatitude) {
        this.centerLatitude = initialLatitude;
    }

    public Integer getNumberOfUsers(){
        return numberOfUsers;
    }

    public Integer getZoomLevel(){
        return zoomLevel;
    }

    public void setZoomLevel(int zoomLevel){
        this.zoomLevel = zoomLevel;
    }

    public Double getPinLongitude() {
        return pinLongitude;
    }

    public Double getPinLatitude() {
        return pinLatitude;
    }

    public void setPinLongitude(double pinLongitude) {
        this.pinLongitude = pinLongitude;
    }

    public void setPinLatitude(double pinLatitude) {
        this.pinLatitude = pinLatitude;
    }


}
