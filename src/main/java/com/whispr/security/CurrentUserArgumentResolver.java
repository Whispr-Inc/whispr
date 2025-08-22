package com.whispr.security;

import lombok.NonNull;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.UUID;

@Component
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class) && parameter.getParameterType().equals(UUID.class);
    }

    @Override
    public Object resolveArgument(@NonNull MethodParameter parameter, ModelAndViewContainer mavContainer, @NonNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            String userId = auth.getName(); // Assuming the principal's name is the UUID of the user

            try {
                return UUID.fromString(userId);
            } catch (IllegalArgumentException e) {
                // Handle the case where the principal's name is not a valid UUID
                throw new IllegalArgumentException("Invalid UUID format for user ID: " + userId, e);
            }
        }

        if (parameter.getParameterAnnotation(CurrentUser.class).required()) {
            throw new IllegalArgumentException("Current user is required but not found in the security context.");
        }

        return null;
    }
}
