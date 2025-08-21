package com.whispr.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chats")
@Tag(name = "Chats API", description = "Manage conversations and chats")
public class ChatController {

    @GetMapping
    public ResponseEntity<?> listConversations() {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<?> getConversation(@PathVariable String chatId) {
        return ResponseEntity.ok(null);
    }

    @PostMapping
    public ResponseEntity<?> createConversation(@RequestBody Object chatObj) {
        return ResponseEntity.ok(null);
    }

    @PutMapping("/{chatId}")
    public ResponseEntity<?> updateConversation(@PathVariable String chatId, @RequestBody Object chatUpdateReq) {
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{chatId}")
    public ResponseEntity<?> deleteConversation(@PathVariable String chatId) {
        return ResponseEntity.ok(null);
    }
}
