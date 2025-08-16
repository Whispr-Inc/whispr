package com.whispr.service;

import com.whispr.dto.KeycloakUserDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class KeycloakService {

    public KeycloakUserDto getUserById(UUID userId) {
        // TODO: Implement the logic to fetch user data from Keycloak using the provided userId
        return new KeycloakUserDto(); // Return a new KeycloakUser instance for now
    }
}
