package com.whispr.api;

import com.whispr.dto.response.UserProfileResponse;
import com.whispr.entity.UserProfile;
import com.whispr.security.CurrentUser;
import com.whispr.service.ProfileService;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
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
    public UserProfileResponse getCurrentProfile(@CurrentUser UUID currentUserId) {
        return profileService.getProfileForCurrentUser(currentUserId);
    }

    @PatchMapping("/me")
    public ResponseEntity<?> updateCurrentProfile(@CurrentUser UUID currentUserId, @RequestBody UserProfile profile) {
        return ResponseEntity.ok(null);
    }


    /// ---------------- OTHER USER PROFILE MANAGEMENT ----------------

    @GetMapping("/{userId}")
    public UserProfileResponse getUserProfile(@PathVariable("userId") UUID userId, @CurrentUser UUID currentUserId) {
        return profileService.getProfileBasedOnRelation(userId, currentUserId);
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
    public ResponseEntity<?> searchUsers(@RequestParam("query") String query, Pageable page) {
        return ResponseEntity.ok(null);
    }
}
