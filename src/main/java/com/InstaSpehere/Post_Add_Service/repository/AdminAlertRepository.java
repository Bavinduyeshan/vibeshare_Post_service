package com.InstaSpehere.Post_Add_Service.repository;



import com.InstaSpehere.Post_Add_Service.model.AdminAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminAlertRepository extends JpaRepository<AdminAlert, Long> {
    List<AdminAlert> findByIsResolvedFalse();
    List<AdminAlert> findByPostId(Integer postId);

    boolean existsByPostId(Integer postId);
}