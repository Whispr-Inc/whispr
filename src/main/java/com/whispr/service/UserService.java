package com.whispr.service;

import com.whispr.entity.User;
import com.whispr.repository.UserRepository;
import com.whispr.security.UserSynchronizer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserSynchronizer userSynchronizer;

    /**
     * Retrieves a User by their ID, synchronizing with the database if necessary.
     */
    public User findUserById(UUID userId) {
        if (!userRepository.existsById(userId)) {
            userSynchronizer.synchronizeUserToDB(userId);   // Synchronize user data to DB
        }

        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
