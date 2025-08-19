package com.whispr.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.whispr.entity.Profile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserLookupService {

    private static final String DEFAULT_USERNAME = "unknown";
    private static final String DEFAULT_DISPLAY_NAME = "Unknown User";

    private final ProfileService profileService;

    public String getUserNameById(UUID userId) {
        try {
            Profile profile = profileService.fetchProfileFromDB(userId);
            return profile.getUsername();
        } catch (Exception e) {}

        return DEFAULT_USERNAME;
    }

    public String getUserDisplayNameById(UUID userId) {
        try {
            Profile profile = profileService.fetchProfileFromDB(userId);
            return profile.getFirstName() + " " + profile.getLastName();
        } catch (Exception e) {}

        return DEFAULT_DISPLAY_NAME;
    }
}
