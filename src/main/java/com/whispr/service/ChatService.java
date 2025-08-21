package com.whispr.service;

import com.whispr.dto.response.ChatResponse;
import com.whispr.mapper.ChatMapper;
import com.whispr.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository conversationRepository;
    private final ChatMapper conversationMapper;

    private final UserLookupService userLookupService;

    public List<ChatResponse> listConversations(UUID userId) {
        return new ArrayList<>();
    }

}
