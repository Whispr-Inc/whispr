package com.whispr.service;

import com.whispr.dto.response.UserProfileResponse;
import com.whispr.entity.UserProfile;
import com.whispr.enums.VisibilityScope;
import com.whispr.mapper.ProfileMapper;
import com.whispr.repository.UserProfileRepository;
import com.whispr.security.ProfileSynchronizer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final ProfileSynchronizer profileSynchronizer;

    public UserProfileResponse getProfileForCurrentUser(UUID currentUserId) {
        UserProfile profile = fetchProfileFromDB(currentUserId);
        return profileMapper.toProfileResponse(profile, VisibilityScope.SELF);
    }

    public UserProfileResponse getProfileBasedOnRelation(UUID requestedUserId, UUID currentUserId) {
        UserProfile profile = fetchProfileFromDB(requestedUserId);
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

    protected UserProfile fetchProfileFromDB(UUID profileId) throws NoSuchElementException {
        // Fetch the profile from the repository, or synchronize it if not found
        if (!profileRepository.existsById(profileId)) {
            profileSynchronizer.synchronizeProfile(profileId);
        }

        return profileRepository.findById(profileId).orElseThrow(() -> new NoSuchElementException("No profile found with id: " + profileId));
    }
}
