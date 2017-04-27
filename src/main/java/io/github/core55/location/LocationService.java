package io.github.core55.location;

import io.github.core55.meetup.MeetupRepository;
import io.github.core55.user.User;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by S. Stefani on 2017-04-27.
 */
public class LocationService {

    @Autowired
    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public void updateUserLocationList(User user) {
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
    }
}
