package com.InstaSpehere.Post_Add_Service.repository;



import com.InstaSpehere.Post_Add_Service.model.PostReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostReportRepository extends JpaRepository<PostReport, Long> {
    List<PostReport> findByPostId(Integer postId);
    long countByPostId(Integer postId);
    boolean existsByPostIdAndUserProfileId(Integer postId, Integer userProfileId);
}