/*
  Authors: S. Stefani
 */

package io.github.core55.location;

import io.github.core55.user.User;
import org.springframework.beans.factory.annotation.Autowired;

public class LocationService {

    @Autowired
    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    /**
     * When the position of a user is updated, a new Location needs to be linked to the user. However only the last ten
     * positions of the users are tracked and can be retrieved at /users/{id}⁄locations.
     *
     * @param user with updated fields lastLongitude and lastLatitude
     */
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
