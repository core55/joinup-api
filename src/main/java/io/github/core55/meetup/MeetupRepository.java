/**
 * Meetup.java
 *
 * Created by S. Stefani on 2017-04-20.
 * Edited by P. Gajland on 2017-04-21.
 */

package io.github.core55.meetup;

import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;

public interface MeetupRepository extends CrudRepository<Meetup, Long> {
    Meetup findByHash(@Param("hash") String hash);
}