/**
 * LocationRepository.java
 *
 * Created by S. Stefani on 2017-04-26.
 */

package io.github.core55.location;

import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, Long> {
}