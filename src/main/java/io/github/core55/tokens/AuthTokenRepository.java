/*
  Authors: S. Stefani
 */

package io.github.core55.tokens;

import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface AuthTokenRepository extends CrudRepository<AuthToken, Long> {
    /**
     * Find a AuthToken by its value.
     */
    AuthToken findByValue(@Param("value") String value);
}
