package com.whispr.service;

import com.whispr.entity.Profile;
import com.whispr.repository.ProfileRepository;
import com.whispr.security.ProfileSynchronizer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileSynchronizer profileSynchronizer;

    /**
     * Retrieves a profile from database by its ID, synchronizing it if it does not exist.
     *
     * @param profileId the ID of the profile to retrieve
     * @return the Profile entity
     * @throws NoSuchElementException if no profile is found with the given ID
     */
    private Profile findProfileById(UUID profileId) {
        // Fetch the profile from the repository, or synchronize it if not found
        if (!profileRepository.existsById(profileId)) {
            profileSynchronizer.synchronizeProfile(profileId);
        }

        return profileRepository.findById(profileId).orElseThrow(() -> new NoSuchElementException("No profile found with id: " + profileId));
    }
}
