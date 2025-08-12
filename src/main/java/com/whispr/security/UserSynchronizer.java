package com.whispr.security;

import com.whispr.entity.User;
import com.whispr.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserSynchronizer {

    private final UserRepository userRepository;

    public void synchronizeUserToDB(UUID userId) {
        log.info("Synchronizing user {} to DB", userId);
        int randomSuffix = (int) (Math.random() * 1000); // Generate a random suffix for the username

        User newUser = new User();

        newUser.setId(userId);
        newUser.setUsername("Anonymous" + randomSuffix);
        newUser.setEmail("anonymous" + randomSuffix + "@example.com");
        newUser.setFirstName("Anonymous");
        newUser.setLastName("User");
        newUser.setBio("This is an anonymous user created by the system.");
        newUser.setIsActive(true);

        userRepository.save(newUser);
        log.info("New user {} created and saved to DB", userId);
    }
}
