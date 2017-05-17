/*
  Authors: S. Stefani
 */

package io.github.core55.user;

import org.springframework.stereotype.Service;import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class DetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * The authentication logic needs to retrieve user-specific data. The method loadUserByUsername finds a user entity
     * based on the username and it is overridden to customize the process of finding the user.
     *
     * @param username of the user
     * @return a specific Spring Security User object (different from the app-specific User entity)
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(username + "could not be found!");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                AuthorityUtils.createAuthorityList(String.valueOf(user.getRoles()))
        );
    }
}
