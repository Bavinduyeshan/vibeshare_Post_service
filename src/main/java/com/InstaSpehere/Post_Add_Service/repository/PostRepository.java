package com.InstaSpehere.Post_Add_Service.repository;

import com.InstaSpehere.Post_Add_Service.model.PostModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<PostModel, Integer> {
    List<PostModel> findByUserProfileId(Integer userProfileId);

    List<PostModel> findByUserProfileIdIn(List<Integer> userProfileIds);

    List<PostModel> findByIsBannedTrue();
}