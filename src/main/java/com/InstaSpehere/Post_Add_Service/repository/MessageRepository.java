package com.InstaSpehere.Post_Add_Service.repository;


import com.InstaSpehere.Post_Add_Service.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderIdAndReceiverIdOrReceiverIdAndSenderId(
            Integer senderId, Integer receiverId, Integer receiverId2, Integer senderId2);
    List<Message> findByReceiverId(Integer receiverId);
    List<Message> findBySenderId(Integer senderId);
}