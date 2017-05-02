package io.github.core55.user;

import org.springframework.stereotype.Component;
import io.github.core55.location.LocationService;
import io.github.core55.location.LocationRepository;
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

    /**
     * Set createdAt and updatedAt fields when a User entity is created.
     */
    @HandleBeforeCreate
    public void setUserTimestampsOnCreate(User user) {
        user.setCreatedAt();
        user.setUpdatedAt();
    }

    /**
     * Set updatedAt field when a User entity is updated.
     */
    @HandleBeforeSave
    public void setUserTimestampOnUpdate(User user) {
        user.setUpdatedAt();
    }

    /**
     * Set hash field when a User entity is created.
     */
    @HandleBeforeCreate
    public void setUserHash(User user) {
        UserService userService = new UserService(userRepository);
        user.setUsername(userService.generateHash());
    }

    /**
     * Add latest user position as a Location entity if new coordinates are provided.
     */
    @HandleBeforeSave
    public void syncLocationListOnUpdate(User user) {
        if (user.getLastLatitude() != null && user.getLastLongitude() != null) {
            LocationService locationService = new LocationService(locationRepository);
            locationService.updateUserLocationList(user);
        }
    }
}
