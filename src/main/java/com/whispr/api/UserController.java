package com.whispr.api;

import com.whispr.entity.User;
import com.whispr.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/search")
    public ResponseEntity<?> searchUsers(@RequestParam("q") String query) {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUserProfile(Principal principal) {
        try {
            UUID uuid = UUID.fromString(principal.getName());
            User userById = userService.findUserById(uuid);
            return ResponseEntity.ok(userById);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/me")
    public ResponseEntity<?> updateCurrentUserProfile(@RequestBody Object userProfile) {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserProfile(@PathVariable String userId) {
        return ResponseEntity.ok(null);
    }

    @PostMapping("/{userId}/report")
    public ResponseEntity<?> reportUser(@PathVariable String userId, @RequestBody Object report) {
        return ResponseEntity.ok(null);
    }

    @PostMapping("/{userId}/block")
    public ResponseEntity<?> blockUser(@PathVariable String userId) {
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{userId}/block")
    public ResponseEntity<?> unblockUser(@PathVariable String userId) {
        return ResponseEntity.ok(null);
    }
}
