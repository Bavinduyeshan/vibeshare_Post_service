package com.InstaSpehere.Post_Add_Service.repository;

import com.InstaSpehere.Post_Add_Service.model.CommentReply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentReplyRepository extends JpaRepository<CommentReply, Long> {
    List<CommentReply> findByCommentId(Long commentId);
}