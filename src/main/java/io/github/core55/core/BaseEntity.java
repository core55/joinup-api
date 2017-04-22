/**
 * BaseEntity.java
 *
 * Created by S. Stefani on 2017-04-22.
 */

package io.github.core55.core;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Long id;

    protected BaseEntity() {
        id = null;
    }
}
