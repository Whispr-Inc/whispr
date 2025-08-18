package com.whispr.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;
import java.util.UUID;

/**
 * Represents a user profile in the system.
 * This entity is used to store user profile information.
 */
@Getter
@Setter
@Entity
@Table(name = "profiles")
@NoArgsConstructor
public class Profile extends BaseEntity<UUID> {

    @Id
    private UUID id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "bio")
    private String bio;

    @Column(name = "joined_at", nullable = false)
    private Instant joinedAt;

    @Column(name = "last_synced_at", nullable = false)
    private Instant lastSyncedAt;

    @Column(name = "last_seen")
    private Instant lastSeen;
}
