/**
 * UserRepository.java
 *
 * Created by C. Seger on 2017-04-21.
 * Edited by P. Gajland on 2017-04-24.
 */

package io.github.core55.user;

import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByHash(@Param("hash") String hash);
}