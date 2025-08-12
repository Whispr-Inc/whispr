package com.whispr.security;

import java.util.UUID;

/**
 * Interface for synchronizing user profiles with an external system, such as Keycloak.
 * Implementations should provide the logic to fetch and update profile data.
 */
public interface ProfileSynchronizer {

    /**
     * Synchronizes a user profile with the external system.
     *
     * @param profileId the ID of the profile to synchronize
     */
    void synchronizeProfile(UUID profileId);
}
