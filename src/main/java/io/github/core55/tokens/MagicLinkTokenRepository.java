/*
  Authors: S. Stefani
 */

package io.github.core55.tokens;

import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface MagicLinkTokenRepository extends CrudRepository<MagicLinkToken, Long> {
    /**
     * Find a MagiLinkToken by its value.
     */
    MagicLinkToken findByValue(@Param("value") String value);
}
