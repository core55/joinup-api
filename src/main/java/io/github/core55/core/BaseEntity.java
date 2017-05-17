/*
  Authors: S. Stefani
 */

package io.github.core55.core;

import org.springframework.format.annotation.DateTimeFormat;

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

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private String createdAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
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
        this.createdAt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(new Date());
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt() {
        this.updatedAt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(new Date());
    }
}
