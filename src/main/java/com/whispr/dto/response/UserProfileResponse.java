package com.whispr.dto.response;

import java.time.Instant;
import java.util.UUID;

import lombok.Data;

@Data
public class UserProfileResponse {

    private UUID id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String bio;
    private Instant joinedAt;
    private Instant lastSeen;
}
