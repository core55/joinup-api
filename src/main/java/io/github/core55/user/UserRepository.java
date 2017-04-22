/**
 * UserRepository.java
 *
 * Created by C. Seger on 2017-04-21.
 */

package io.github.core55.user;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}