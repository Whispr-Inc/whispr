package com.whispr.repository;

import com.whispr.entity.Chat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChatRepository extends JpaRepository<Chat, UUID> {

    @Query(name = Chat.FIND_ALL_USER_CHATS, nativeQuery = true)
    Page<Chat> findAllUserChats(UUID userId, Pageable pageable);
}
