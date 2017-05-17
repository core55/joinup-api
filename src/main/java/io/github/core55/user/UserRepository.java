/*
  Authors: S. Stefani, P. Gajland
 */

package io.github.core55.user;

import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;

public interface UserRepository extends CrudRepository<User, Long> {
    /**
     * Find a User entity by its username.
     */
    User findByUsername(@Param("username") String username);

    @Override
    @PreAuthorize("@userRepository.findOne(#id)?.username == authentication.name")
    void delete(@Param("id") Long id);
}
