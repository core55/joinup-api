package io.github.core55.user;

import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    /**
     * Find a User entity by its username.
     */
    User findByUsername(@Param("username") String username);

    /**
     * Find a User entity by its authenticationToken.
     */
    User findByAuthenticationToken(@Param("authenticationToken") String authenticationToken);
}