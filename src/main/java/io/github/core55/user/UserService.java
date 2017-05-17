/*
  Authors: S. Stefani, P. Gajland
 */

package io.github.core55.user;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Generate a unique hash for a User entity which is set as initial username.
     *
     * @return the newly created hash.
     */
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
