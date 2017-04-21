/**
 * MeetupRepository.java
 *
 * Created by S. Stefani on 2017-04-20.
 */

package io.core55.drycherry;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MeetupRepository extends CrudRepository<Meetup, Long> {

    Meetup findByHash(String hash);
}