package io.core55.drycherry;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by S. Stefani on 2017-04-20.
 */

@Entity
public class Meetup {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private double initialLongitude;
    private double initialLatitude;

//    private double pinLongitude;
//    private double pinLatitude;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
