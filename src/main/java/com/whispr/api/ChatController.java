package com.whispr.api;

import com.whispr.dto.response.ChatResponse;
import com.whispr.security.CurrentUser;
import com.whispr.service.ChatService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/chats")
@RequiredArgsConstructor
@Tag(name = "Chats API", description = "Manage conversations and chats")
public class ChatController {

    private final ChatService chatService;

    @GetMapping
    public Page<ChatResponse> listConversations(@CurrentUser UUID currentUser, Pageable pageable) {
        return chatService.listConversations(currentUser, pageable);
    }


    // Uncomment and implement these methods as needed

    // @GetMapping("/{chatId}")
    // public ResponseEntity<?> getConversation(@PathVariable String chatId) {
    //     return ResponseEntity.ok(null);
    // }

    // @PostMapping
    // public ResponseEntity<?> createConversation(@RequestBody Object chatObj) {
    //     return ResponseEntity.ok(null);
    // }

    // @PutMapping("/{chatId}")
    // public ResponseEntity<?> updateConversation(@PathVariable String chatId, @RequestBody Object chatUpdateReq) {
    //     return ResponseEntity.ok(null);
    // }

    // @DeleteMapping("/{chatId}")
    // public ResponseEntity<?> deleteConversation(@PathVariable String chatId) {
    //     return ResponseEntity.ok(null);
    // }
}
