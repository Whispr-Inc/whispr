package com.whispr.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class KeycloakUserDto {

    private UUID id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private boolean enabled;
}
