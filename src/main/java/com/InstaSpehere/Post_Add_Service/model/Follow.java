package com.InstaSpehere.Post_Add_Service.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "follows")
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long followId;

    @Column(name = "follower_id", nullable = false)
    private Integer followerId;

    @Column(name = "followed_id", nullable = false)
    private Integer followedId;

    @Column(name = "created_at", updatable = false, insertable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;


    @Transient
    private String username;

    @Transient
    private String profilePictureUrl;

    // Getters and Setters
    public Long getFollowId() { return followId; }
    public void setFollowId(Long followId) { this.followId = followId; }
    public Integer getFollowerId() { return followerId; }
    public void setFollowerId(Integer followerId) { this.followerId = followerId; }
    public Integer getFollowedId() { return followedId; }
    public void setFollowedId(Integer followedId) { this.followedId = followedId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }
}