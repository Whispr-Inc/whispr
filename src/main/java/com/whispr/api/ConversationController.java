package com.whispr.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/conversations")
@Tag(name = "Conversations", description = "Manage conversations")
public class ConversationController {

    @GetMapping
    public ResponseEntity<?> listConversations() {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/{conversationId}")
    public ResponseEntity<?> getConversation(@PathVariable String conversationId) {
        return ResponseEntity.ok(null);
    }

    @PostMapping
    public ResponseEntity<?> createConversation(@RequestBody Object conversationRequest) {
        return ResponseEntity.ok(null);
    }

    @PutMapping("/{conversationId}")
    public ResponseEntity<?> updateConversation(@PathVariable String conversationId, @RequestBody Object conversationUpdate) {
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{conversationId}")
    public ResponseEntity<?> deleteConversation(@PathVariable String conversationId) {
        return ResponseEntity.ok(null);
    }
}
