package com.whispr.entity;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import com.whispr.enums.ChatType;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "chat")
@NoArgsConstructor
@NamedQuery(name = Chat.FIND_ALL_USER_CHATS,
    query = "SELECT c FROM Chat c JOIN ChatMember cm ON cm.chatId = c.id WHERE cm.userId = :userId AND c.deletedAt IS NULL")
public class Chat extends BaseEntity<UUID> {

    public static final String FIND_ALL_USER_CHATS = "Chat.findAllUserChats";

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ChatType type;

    @Column(name = "title")
    private String title;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private UUID createdBy;

    @Column(name = "deleted_at")
    private Instant deletedAt;
}
