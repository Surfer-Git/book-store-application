package com.surfer.codes.order_service.domain;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    public String getLoggedInUserName() {
                JwtAuthenticationToken authentication =
                        (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
                OAuth2ResourceServerProperties.Jwt jwt = (OAuth2ResourceServerProperties.Jwt) authentication.getPrincipal();
                /*
                var username = jwt.getClaimAsString("preferred_username");
                var email = jwt.getClaimAsString("email");
                var name = jwt.getClaimAsString("name");
                var token = jwt.getTokenValue();
                var authorities = authentication.getAuthorities();
                */
                return jwt.getClaimAsString("preferred_username");
    }
}
