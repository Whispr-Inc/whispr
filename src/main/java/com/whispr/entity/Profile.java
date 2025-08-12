package com.whispr.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
}
