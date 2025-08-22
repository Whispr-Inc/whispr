package com.whispr.service;

import com.whispr.dto.response.ChatResponse;
import com.whispr.mapper.ChatMapper;
import com.whispr.repository.ChatRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;

    public Page<ChatResponse> listConversations(@NonNull UUID currentUser, @NonNull Pageable pageable) {
        return chatRepository.findAllUserChats(currentUser, pageable)
            .map(chat -> chatMapper.toResponse(chat, currentUser));
    }
}
