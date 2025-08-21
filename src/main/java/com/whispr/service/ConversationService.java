package com.whispr.service;

import com.whispr.dto.response.ConversationResponse;
import com.whispr.mapper.ConversationMapper;
import com.whispr.repository.ConversationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final ConversationMapper conversationMapper;

    private final UserLookupService userLookupService;

    public List<ConversationResponse> listConversations(UUID userId) {
        return new ArrayList<>();
    }

}
