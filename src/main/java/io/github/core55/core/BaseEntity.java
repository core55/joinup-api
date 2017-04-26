/**
 * BaseEntity.java
 *
 * Created by S. Stefani on 2017-04-22.
 */

package io.github.core55.core;

import java.util.Date;
import javax.persistence.*;
import java.text.SimpleDateFormat;

@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Long id;

    @Version
    private Long version;

    private String createdAt;
    private String updatedAt;

    protected BaseEntity() {
        id = null;
    }

    public Long getId() {
        return id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt() {
        this.createdAt = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt() {
        this.updatedAt = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    }
}
