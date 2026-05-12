package animalDex.dex.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name =  "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "profile_picture_num", nullable = false)
    private Integer profilePicNum;

    @Column(nullable = false)
    private Integer xp;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public User(String username, String password, Integer profilePicNum) {
        this.username = username;
        this.password = password;
        this.profilePicNum = profilePicNum;
        this.xp = 100;
        this.createdAt = LocalDateTime.now();
    }

    public User() {
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
        this.password = password;
    }

    public Integer getProfilePicNum() {
        return profilePicNum;
    }

    public void setProfilePicNum(Integer profilePicNum) {
        this.profilePicNum = profilePicNum;
    }

    public Integer getXp() {
        return xp;
    }

    public void setXp(Integer xp) {
        this.xp = xp;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
