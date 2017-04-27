package io.github.core55.user;

import io.github.core55.location.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * Created by S. Stefani on 2017-04-27.
 */
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String generateHash() {
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
