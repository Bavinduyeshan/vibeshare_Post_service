package com.InstaSpehere.Post_Add_Service.repository;


import com.InstaSpehere.Post_Add_Service.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    boolean existsByFollowerIdAndFollowedId(Integer followerId, Integer followedId);
    Optional<Follow> findByFollowerIdAndFollowedId(Integer followerId, Integer followedId);
    List<Follow> findByFollowerId(Integer followerId);
    List<Follow> findByFollowedId(Integer followedId);
}