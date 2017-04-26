/**
 * UserEventHandler.java
 *
 * Created by P. Gajland on 2017-04-24.
 */

package io.github.core55.user;

import java.util.UUID;

import io.github.core55.location.Location;
import io.github.core55.location.LocationRepository;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

@Component
@RepositoryEventHandler
public class UserEventHandler {

    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

    @Autowired
    public UserEventHandler(UserRepository userRepository, LocationRepository locationRepository) {
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
    }

    @HandleBeforeCreate
    public void setUserTimestampsOnCreate(User user) {

        Location newLocation = new Location(user.getLastLongitude(), user.getLastLatitude(), user);
        newLocation.setCreatedAt();
        newLocation.setUpdatedAt();

        if (user.getLocations().size() >= 10) {
            Location oldestLocation = user.getLocations().get(0);
            user.getLocations().remove(0);
            locationRepository.delete(oldestLocation);
        }

        user.getLocations().add(newLocation);
        locationRepository.save(newLocation);

        user.setCreatedAt();
        user.setUpdatedAt();
    }

    @HandleBeforeSave
    public void setUserTimestampOnUpdate(User user) {

        Location newLocation = new Location(user.getLastLongitude(), user.getLastLatitude(), user);
        newLocation.setCreatedAt();
        newLocation.setUpdatedAt();

        if (user.getLocations().size() >= 10) {
            Location oldestLocation = user.getLocations().get(0);
            user.getLocations().remove(0);
            locationRepository.delete(oldestLocation);
        }

        user.getLocations().add(newLocation);
        locationRepository.save(newLocation);

        user.setUpdatedAt();
    }

    @HandleBeforeCreate
    public void setUserHash(User user) {
        user.setUsername(generateHash());
    }

    private String generateHash() {
        int count = 0;
        boolean flag = false;
        String hash;

        while (!flag && count < 10) {
            hash = UUID.randomUUID().toString().replaceAll("-", "");
            User user = userRepository.findByUsername(hash);
            if (user == null) {
                flag = true;
                return hash;
            }
            count++;
        }

        return null;
    }
}