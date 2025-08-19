package com.whispr.service;

import com.whispr.dto.response.ProfileResponse;
import com.whispr.entity.Profile;
import com.whispr.enums.VisibilityScope;
import com.whispr.mapper.ProfileMapper;
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
    private final ProfileMapper profileMapper;
    private final ProfileSynchronizer profileSynchronizer;

    public ProfileResponse getProfileForCurrentUser(UUID currentUserId) {
        Profile profile = fetchProfileFromDB(currentUserId);
        return profileMapper.toProfileResponse(profile, VisibilityScope.SELF);
    }

    public ProfileResponse getProfileBasedOnRelation(UUID requestedUserId, UUID currentUserId) {
        Profile profile = fetchProfileFromDB(requestedUserId);
        VisibilityScope visibilityScope = determineVisibilityScope(requestedUserId, currentUserId);
        return profileMapper.toProfileResponse(profile, visibilityScope);
    }

    private VisibilityScope determineVisibilityScope(UUID requestedUserId, UUID currentUserId) {
        if (requestedUserId.equals(currentUserId)) {
            return VisibilityScope.SELF;
        }

        // Logic to determine if the user is a friend or public
        // This could involve checking a friendship table or similar logic
        // For simplicity, we assume FRIENDS visibility for now
        return VisibilityScope.FRIENDS; // TODO: This should be replaced with actual logic
    }

    protected Profile fetchProfileFromDB(UUID profileId) throws NoSuchElementException {
        // Fetch the profile from the repository, or synchronize it if not found
        if (!profileRepository.existsById(profileId)) {
            profileSynchronizer.synchronizeProfile(profileId);
        }

        return profileRepository.findById(profileId).orElseThrow(() -> new NoSuchElementException("No profile found with id: " + profileId));
    }
}
