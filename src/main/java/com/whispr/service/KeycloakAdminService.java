package com.whispr.service;

import com.whispr.dto.KeycloakUserDto;
import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KeycloakAdminService {

    @Value("${keycloak.server-url}")
    private String serverUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.admin-username}")
    private String adminUsername;

    @Value("${keycloak.admin-password}")
    private String adminPassword;

    @Value("${keycloak.client-id:admin-cli}")
    private String clientId;                    // Default to "admin-cli" if not specified

    private Keycloak keycloak;

    @PostConstruct
    protected void initKeycloak() {
        this.keycloak = KeycloakBuilder.builder()
            .serverUrl(serverUrl)
            .realm("master") // Admin realm
            .username(adminUsername)
            .password(adminPassword)
            .clientId(clientId)
            .build();
    }

    public KeycloakUserDto getUserById(UUID userId) {
        // Validate the userId and keycloak instance before proceeding
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        UsersResource usersResource = keycloak.realm(realm).users();
        UserRepresentation kcUser = usersResource.get(userId.toString()).toRepresentation();

        if (kcUser == null) {
            throw new NotFoundException("Keycloak User not found with ID: " + userId);
        }

        return mapToKeycloakUserDto(kcUser);
    }

    private KeycloakUserDto mapToKeycloakUserDto(UserRepresentation userRep) {
        KeycloakUserDto dto = new KeycloakUserDto();
        dto.setId(UUID.fromString(userRep.getId()));
        dto.setUsername(userRep.getUsername());
        dto.setEmail(userRep.getEmail());
        dto.setFirstName(userRep.getFirstName());
        dto.setLastName(userRep.getLastName());
        dto.setEnabled(userRep.isEnabled());
        return dto;
    }
}
