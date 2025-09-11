package com.InstaSpehere.Post_Add_Service.model;

import jakarta.persistence.Transient;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserprofileResponse {


    private Integer profileId;


    private Integer userId;  // coming from another service (no FK here)


    private String fullName;


    private String bio;


    private String profilePictureUrl;


    private String website;


    private String location;


    private String email;


    private String phoneNumber;


    private String gender;


    private LocalDate birthDate;


    private Boolean isPrivate = false;


    private Boolean isVerified = false;


    private Boolean isActive = true;


    private LocalDateTime lastLogin;


    private LocalDateTime createdAt;


    private LocalDateTime updatedAt;


    public Integer getProfileId() {
        return profileId;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getBio() {
        return bio;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public String getWebsite() {
        return website;
    }

    public String getLocation() {
        return location;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Boolean getPrivate() {
        return isPrivate;
    }

    public Boolean getVerified() {
        return isVerified;
    }

    public Boolean getActive() {
        return isActive;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Transient
    private String username;


    public String getUsername() {
        return username;
    }
}
