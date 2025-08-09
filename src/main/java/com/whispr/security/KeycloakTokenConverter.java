package com.whispr.security;

import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class KeycloakTokenConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    // Default converter for extracting roles from JWT
    private static final JwtGrantedAuthoritiesConverter DEFAULT_CONVERTER = new JwtGrantedAuthoritiesConverter();

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt source) {
        Collection<GrantedAuthority> defaultRoles = DEFAULT_CONVERTER.convert(source);
        Collection<GrantedAuthority> resourceRoles = extractResourceRoles(source);

        Collection<GrantedAuthority> combinedRoles = Stream.concat(
            defaultRoles.stream(),
            resourceRoles.stream()
        ).collect(Collectors.toSet());

        return new JwtAuthenticationToken(source, combinedRoles);
    }

    @SuppressWarnings("unchecked")
    private Collection<GrantedAuthority> extractResourceRoles(@NonNull Jwt source) {
        // TODO: Replace with a more generic way to extract roles if needed
        // This assumes the resource access structure is consistent with Keycloak's default.

        var resourceAccess = source.getClaimAsMap("resource_access");
        var eternal = (Map<String, List<String>>) resourceAccess.get("account");
        var roles = eternal.get("roles");

        return roles.stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.replace("-", "_")))
            .collect(Collectors.toSet());
    }
}
