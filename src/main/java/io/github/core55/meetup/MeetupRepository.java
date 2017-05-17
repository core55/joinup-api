/*
  Authors: S. Stefani
 */

package io.github.core55.meetup;

import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;

public interface MeetupRepository extends CrudRepository<Meetup, Long> {
    /**
     * Find a Meetup entity by its hash value.
     */
    Meetup findByHash(@Param("hash") String hash);
}