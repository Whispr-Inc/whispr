package com.whispr.api;

import com.whispr.entity.Profile;
import com.whispr.security.CurrentUser;
import com.whispr.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/search")
    public ResponseEntity<?> searchUsers(@RequestParam("q") String query) {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUserProfile(@CurrentUser UUID currentUserId) {
        Profile profile = profileService.findProfileById(currentUserId);
        return ResponseEntity.ok(profile);
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
