package io.github.core55.tokens;

import java.util.Date;
import javax.persistence.Id;
import javax.persistence.Entity;
import java.text.SimpleDateFormat;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
public class AuthToken {

    public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Long id;

    private Long userId;

    private String value;

    private String action;

    private String username;

    private String password;

    private String createdAt;

    protected AuthToken() {
        this.id = null;
    }

    public AuthToken(String value, String username, String password) {
        this.id = null;
        this.value = value;
        this.username = username;
        this.setPassword(password);
        this.setCreatedAt();
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = PASSWORD_ENCODER.encode(password);
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt() {
        this.createdAt = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    }
}
