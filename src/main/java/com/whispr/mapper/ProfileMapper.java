package com.whispr.mapper;

import org.springframework.stereotype.Component;

import com.whispr.dto.response.ProfileResponse;
import com.whispr.entity.UserProfile;
import com.whispr.enums.VisibilityScope;

@Component
public class ProfileMapper {

    public ProfileResponse toProfileResponse(UserProfile profile, VisibilityScope visibilityScope) {
        if (profile == null) {
            return null;
        }

        // Map Profile entity to ProfileResponse DTO
        ProfileResponse profileResponse = new ProfileResponse();
        profileResponse.setId(profile.getId());
        profileResponse.setUsername(profile.getUsername());
        profileResponse.setEmail(profile.getEmail());
        profileResponse.setFirstName(profile.getFirstName());
        profileResponse.setLastName(profile.getLastName());
        profileResponse.setBio(profile.getBio());
        profileResponse.setJoinedAt(profile.getJoinedAt());

        if (visibilityScope.isAtLeast(VisibilityScope.FRIENDS)) {
            profileResponse.setLastSeen(profile.getLastSeen());
        }

        return profileResponse;
    }
}
