package com.InstaSpehere.Post_Add_Service.repository;


import com.InstaSpehere.Post_Add_Service.model.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface StoryRepository extends JpaRepository<Story, Long> {
    List<Story> findByUserProfileId(Integer userProfileId);

    @Query("SELECT s FROM Story s WHERE s.createdAt >= :expiryTime")
    List<Story> findActiveStories(LocalDateTime expiryTime);

    @Query("SELECT s FROM Story s WHERE s.userProfileId = :userProfileId AND s.createdAt >= :expiryTime")
    List<Story> findActiveStoriesByUserProfileId(Integer userProfileId, LocalDateTime expiryTime);
}