package io.github.core55.user;

import java.util.List;
import javax.persistence.*;
import java.util.ArrayList;
import io.github.core55.meetup.Meetup;
import io.github.core55.core.BaseEntity;
import javax.validation.constraints.Size;
import io.github.core55.location.Location;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotNull;

@Entity
public class User extends BaseEntity {

    public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Size(min = 1, max = 50)
    private String nickname;

    private Double lastLongitude;

    private Double lastLatitude;

    @NotNull
    private String username;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private String[] roles;

    @Size(min = 1, max = 50)
    private String status;

    private String gravatarURI;

    @JsonIgnore
    private String token;

    @ManyToMany
    @JoinTable(
            name = "meetup_user",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "meetup_id")})
    private List<Meetup> meetups = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Location> locations;

    protected User() {
        super();
    }

    public User(Double lastLongitude, Double lastLatitude) {
        this();
        this.lastLongitude = lastLongitude;
        this.lastLatitude = lastLatitude;
        setCreatedAt();
        setUpdatedAt();
    }

    public User(String username, String password, String gravatarURI) {
        this.username = username;
        this.setPassword(password);
        this.gravatarURI = gravatarURI;
        setCreatedAt();
        setUpdatedAt();
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

    public List<Meetup> getMeetups() {
        return meetups;
    }

    public void setMeetups(List<Meetup> meetups) {
        this.meetups = meetups;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = PASSWORD_ENCODER.encode(password);
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGravatarURI() {
        return gravatarURI;
    }

    public void setGravatarURI(String gravatarURI) {
        this.gravatarURI = gravatarURI;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
