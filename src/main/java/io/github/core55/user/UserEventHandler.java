/**
 * UserEventHandler.java
 *
 * Created by P. Gajland on 2017-04-24.
 */

package io.github.core55.user;

import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

@Component
@RepositoryEventHandler
public class UserEventHandler {

    private final UserRepository userRepository;

    @Autowired
    public UserEventHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @HandleBeforeCreate
    public void setUserTimestampsOnCreate(User user) {
        user.setCreatedAt();
        user.setUpdatedAt();
    }

    @HandleBeforeSave
    public void setUserTimestampOnUpdate(User user) {
        user.setUpdatedAt();
    }

    @HandleBeforeCreate
    public void setUserHash(User user) {
        user.setHash(generateHash());
    }

    private String generateHash() {
        int count = 0;
        boolean flag = false;
        String hash;

        while (!flag && count < 10) {
            hash = UUID.randomUUID().toString().replaceAll("-", "");
            User user = userRepository.findByHash(hash);
            if (user == null) {
                flag = true;
                return hash;
            }
            count++;
        }

        return null;
    }
}