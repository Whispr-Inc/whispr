package com.whispr.security;

import com.whispr.entity.Profile;
import com.whispr.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Implementation of ProfileSynchronizer that synchronizes user profiles with Keycloak.
 * This class fetches profile data from Keycloak and updates the local repository.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class KeycloakProfileSynchronizer implements ProfileSynchronizer {

    private final ProfileRepository profileRepository;

    @Transactional
    @Override
    public void synchronizeProfile(UUID profileId) {
        try {
            // Fetch profile data from Keycloak

            // Fetch the profile from the repository, or create a new one if it doesn't exist
            Profile profile = profileRepository.findById(profileId)
                    .orElseGet(() -> {
                        Profile newProfile = new Profile();
                        newProfile.setId(profileId);
                        return newProfile;
                    });

            // Update profile data with Keycloak data
            // This is a placeholder for actual Keycloak data fetching logic

            profileRepository.save(profile);
            log.debug("Profile synchronized successfully: {}", profileId);
        } catch (Exception e) {
            log.error("Failed to synchronize profile with ID: {}", profileId, e);
        }
    }
}
