package com.dinlurceis.smartmed.service.impl;

import com.dinlurceis.smartmed.service.SecurityService;
import com.nimbusds.jose.JWSObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class SecurityServiceImpl implements SecurityService {
    @Override
    public boolean isCurrentUser(Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.debug("Checking authorization - Target userId: {}", userId);

        if (authentication == null || !authentication.isAuthenticated()) {
            log.debug("Authentication is null or not authenticated");
            return false;
        }

        try {
            // Lấy JWSObject từ authentication
            JWSObject jwsObject = (JWSObject) authentication.getCredentials();
            Map<String, Object> claims = jwsObject.getPayload().toJSONObject();

            // Lấy userId từ claims
            Long currentUserId = ((Number) claims.get("userId")).longValue();
            log.debug("Current userId from JWT: {}", currentUserId);

            return userId.equals(currentUserId);
        } catch (Exception e) {
            log.error("Error extracting userId from JWT", e);
            return false;
        }
    }
}