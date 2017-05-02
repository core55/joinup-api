package io.github.core55.user;

public class SimpleUser {

    private Long id;
    private String nickname;
    private Double lastLongitude;
    private Double lastLatitude;
    private String username;
    private String createdAt;
    private String updatedAt;
    private String status;

    public SimpleUser(Long id, String nickname, Double lastLongitude, Double lastLatitude, String username, String createdAt, String updatedAt, String status) {
        this.id = id;
        this.nickname = nickname;
        this.lastLongitude = lastLongitude;
        this.lastLatitude = lastLatitude;
        this.username = username;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
    }
}
