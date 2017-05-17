/*
  Authors: S. Stefani
 */

package io.github.core55.tokens;

import java.util.Date;
import javax.persistence.Id;
import javax.persistence.Entity;
import java.text.SimpleDateFormat;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class MagicLinkToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Long id;

    private Long userId;

    private String value;

    private String createdAt;

    protected MagicLinkToken() {
        this.id = null;
    }

    public MagicLinkToken(String value, Long userId) {
        this.id = null;
        this.userId = userId;
        this.value = value;
        setCreatedAt();
    }

    public Long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt() {
        this.createdAt = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    }
}
