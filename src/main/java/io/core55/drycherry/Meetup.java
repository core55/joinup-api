/**
 * Meetup.java
 *
 * Created by S. Stefani on 2017-04-20.
 */

package io.core55.drycherry;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Entity
public class Meetup {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    /**
     * The initial longitude and latitude are determined by the user by centering the map during the creation phase.
     * The exact coordinates are returned from Google Maps APIs.
     */
    private double initialLongitude;
    private double initialLatitude;

//    private double pinLongitude;
//    private double pinLatitude;

    /**
     * Basic getters and setters to interact with the properties of the Meetup model.
     */
    public Integer getId() {
        return id;
    }

    public double getInitialLongitude() {
        return initialLongitude;
    }

    public void setInitialLongitude(double initialLongitude) {
        this.initialLongitude = initialLongitude;
    }

    public double getInitialLatitude() {
        return initialLatitude;
    }

    public void setInitialLatitude(double initialLatitude) {
        this.initialLatitude = initialLatitude;
    }

//    public double getPinLongitude() {
//        return pinLongitude;
//    }
//
//    public void setPinLongitude(double pinLongitude) {
//        this.pinLongitude = pinLongitude;
//    }
//
//    public double getPinLatitude() {
//        return pinLatitude;
//    }
//
//    public void setPinLatitude(double pinLatitude) {
//        this.pinLatitude = pinLatitude;
//    }
}
