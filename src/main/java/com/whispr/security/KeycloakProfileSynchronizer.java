package com.whispr.security;

import com.whispr.dto.KeycloakUserDto;
import com.whispr.entity.UserProfile;
import com.whispr.repository.UserProfileRepository;
import com.whispr.service.KeycloakAdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.Instant;
import java.util.UUID;

/**
 * Implementation of ProfileSynchronizer that synchronizes user profiles with Keycloak.
 * This class fetches profile data from Keycloak and updates the local repository.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class KeycloakProfileSynchronizer implements ProfileSynchronizer {

    private final KeycloakAdminService keycloakService;
    private final UserProfileRepository profileRepository;
    private final TransactionTemplate transactionTemplate;

    @Override
    public void synchronizeProfile(UUID profileId) {
        try {
            log.debug("Starting synchronization for profile with ID: {}", profileId);

            // Validate the profile ID
            if (profileId == null) {
                log.warn("Profile ID is null, skipping synchronization.");
                return;
            }

            // Fetch the profile from Keycloak and upsert it into the local repository
            KeycloakUserDto keycloakProfile = keycloakService.getUserById(profileId);

            // Execute the profile upsert operation within a transaction
            transactionTemplate.execute(
                _ -> upsertProfileInTransaction(profileId, keycloakProfile)
            );

            // Log the successful synchronization
            log.debug("Profile synchronized successfully: {}", profileId);

        } catch (Exception e) {
            // Log the error if synchronization fails
            log.error("Failed to synchronize profile with ID: {}", profileId, e);
        }
    }

    private UserProfile upsertProfileInTransaction(UUID profileId, KeycloakUserDto kcUser) {
        // Fetch the profile from the repository, or create a new one if it doesn't exist
        UserProfile profile = profileRepository.findById(profileId)
            .orElseGet(() -> {
                UserProfile newProfile = new UserProfile();
                newProfile.setId(profileId);
                return newProfile;
            });

        // Update profile data with Keycloak data
        updateProfileFromKeycloak(profile, kcUser);

        // Save the updated profile back to the repository
        return profileRepository.save(profile);
    }

    private void updateProfileFromKeycloak(UserProfile profile, KeycloakUserDto kcUser) {
        profile.setUsername(kcUser.getUsername());
        profile.setEmail(kcUser.getEmail());
        profile.setFirstName(kcUser.getFirstName());
        profile.setLastName(kcUser.getLastName());
        profile.setActive(kcUser.isEnabled());
        profile.setJoinedAt(kcUser.getCreatedTimestamp() != null ? Instant.ofEpochMilli(kcUser.getCreatedTimestamp()) : Instant.now());
        profile.setLastSyncedAt(Instant.now());
    }
}
