package com.whispr.mapper;

import com.whispr.dto.response.ChatResponse;
import com.whispr.entity.Chat;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ChatMapper {

    public ChatResponse toResponse(Chat chat, UUID currentUser) {
        // Mapping logic here
        return new ChatResponse();
    }
}
