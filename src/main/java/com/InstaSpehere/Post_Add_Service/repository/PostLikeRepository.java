package com.InstaSpehere.Post_Add_Service.repository;

import com.InstaSpehere.Post_Add_Service.model.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    List<PostLike> findByPostId(Integer postId);
    boolean existsByPostIdAndUserProfileId(Integer postId, Integer userProfileId);
    Optional<PostLike> findByPostIdAndUserProfileId(Integer postId, Integer userProfileId);
}