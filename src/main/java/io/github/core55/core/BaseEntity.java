/**
 * BaseEntity.java
 *
 * Created by S. Stefani on 2017-04-22.
 */

package io.github.core55.core;

import javax.persistence.*;

@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Long id;

    @Version
    private Long version;

    protected BaseEntity() {
        id = null;
    }

    public Long getId() {
        return id;
    }
}
