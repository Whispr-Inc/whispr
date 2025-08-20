package com.whispr.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/conversations/{conversationId}/participants")
@Tag(name = "Conversation Participants", description = "Manage conversation participants")
public class ParticipantController {

    @GetMapping
    public ResponseEntity<?> getConversationParticipants(@PathVariable String conversationId) {
        return ResponseEntity.ok(null);
    }

    @PostMapping
    public ResponseEntity<?> addConversationParticipant(@PathVariable String conversationId) {
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteConversationParticipant(@PathVariable String conversationId) {
        return ResponseEntity.ok(null);
    }

    @PutMapping("/{userId}/role")
    public ResponseEntity<?> updateConversationParticipantRole(@PathVariable String conversationId) {
        return ResponseEntity.ok(null);
    }
}
