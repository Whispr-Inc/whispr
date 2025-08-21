package com.whispr.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chats/{chatId}/members")
@Tag(name = "Chat Member API", description = "Manage conversation participants")
public class ChatMemberController {

    @GetMapping
    public ResponseEntity<?> getConversationParticipants(@PathVariable String chatId) {
        return ResponseEntity.ok(null);
    }

    @PostMapping
    public ResponseEntity<?> addConversationParticipant(@PathVariable String chatId) {
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteConversationParticipant(@PathVariable String chatId) {
        return ResponseEntity.ok(null);
    }

    @PutMapping("/{userId}/role")
    public ResponseEntity<?> updateConversationParticipantRole(@PathVariable String chatId) {
        return ResponseEntity.ok(null);
    }
}
