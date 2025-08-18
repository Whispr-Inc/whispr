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


    /// ---------------- SELF PROFILE MANAGEMENT ----------------
    
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentProfile(@CurrentUser UUID currentUserId) {
        Profile profile = profileService.findProfileById(currentUserId);
        return ResponseEntity.ok(profile);
    }

    @PatchMapping("/me")
    public ResponseEntity<?> updateCurrentProfile(@CurrentUser UUID currentUserId, @RequestBody Profile profile) {
        return ResponseEntity.ok(null);
    }


    /// ---------------- OTHER USER PROFILE MANAGEMENT ----------------
    
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserProfile(@PathVariable UUID userId) {
        return ResponseEntity.ok(null);
    }

    @PostMapping("/{userId}/report")
    public ResponseEntity<?> reportUser(@PathVariable UUID userId, @RequestBody Object report) {
        return ResponseEntity.ok(null);
    }

    @PostMapping("/{userId}/block")
    public ResponseEntity<?> blockUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{userId}/block")
    public ResponseEntity<?> unblockUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchUsers(@RequestParam("query") String query) {
        return ResponseEntity.ok(null);
    }
}
