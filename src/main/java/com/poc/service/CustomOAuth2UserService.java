package com.poc.service;

import com.poc.entity.User;
import com.poc.service.UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;

@Service
public class CustomOAuth2UserService extends OidcUserService {  // Changed from DefaultOidcUserService to OidcUserService

    private final UserService userService;

    public CustomOAuth2UserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        // Call parent class to get the OIDC user
        OidcUser oidcUser = super.loadUser(userRequest);

        // Process the user data and save if not found
        User user = userService.processOAuth2User(oidcUser);

        // Return enriched OIDC user with DB information
        return oidcUser;
    }
}