/*
  Authors: S. Stefani
 */

package io.github.core55.location;

import javax.persistence.Entity;

import io.github.core55.user.User;

import javax.persistence.ManyToOne;

import io.github.core55.core.BaseEntity;

import javax.validation.constraints.NotNull;

@Entity
public class Location extends BaseEntity {

    @NotNull
    private Double longitude;
    @NotNull
    private Double latitude;

    @ManyToOne
    private User user;

    protected Location() {
        super();
    }

    public Location(Double longitude, Double latitude, User user) {
        this();
        this.longitude = longitude;
        this.latitude = latitude;
        this.user = user;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
