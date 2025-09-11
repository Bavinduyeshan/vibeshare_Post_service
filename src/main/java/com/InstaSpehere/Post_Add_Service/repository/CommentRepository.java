package com.InstaSpehere.Post_Add_Service.repository;

import com.InstaSpehere.Post_Add_Service.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Integer postId);
}