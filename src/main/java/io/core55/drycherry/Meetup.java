/**
 * Meetup.java
 *
 * Created by S. Stefani on 2017-04-20.
 */

package io.core55.drycherry;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "meetup")
public class Meetup implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "created_at")
    private Date createdTime;

    @Column(name = "updated_at")
    private Date updatedTime;

    @Column(length = 20)
    private String name;

    @Column(name = "center_longitude")
    private Double centerLongitude;

    @Column(name = "center_latitude")
    private Double centerLatitude;

    @Column(name = "number_of_users")
    private Integer numberOfUsers;

    @Column(name = "zoom_level")
    private Integer zoomLevel;

    @Column(name = "pin_longitude")
    private Double pinLongitude;

    @Column(name = "pin_latitude")
    private Double pinLatitude;

//    public Meetup(double centerLongitude, double centerLatitude){
//        this.centerLongitude = centerLongitude;
//        this.centerLatitude = centerLatitude;
//    }
//
    public Meetup(String name, double centerLongitude, double centerLatitude) {
        this.name = name;
        this.numberOfUsers = 1;
        this.createdTime = new Date();
        this.updatedTime = new Date();
        this.centerLatitude = centerLatitude;
        this.centerLongitude = centerLongitude;
    }


    public Long getId() {
        return id;
    }

    public Date getCreatedTime(){
        return createdTime;
    }

    public Date getUpdatedTime(){
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
