package com.poc.service;

import com.poc.enums.AuthenticatedBy;
import org.springframework.stereotype.Service;
import com.poc.entity.User;
import com.poc.repository.UserRepository;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Checks if the user exists in the database.
     * Checks if the existing user has AuthenticatedBy set, if not then assign it.
     * If not, create and save a new user.
     * If the user exists, update id_token and expiryTime.
     *
     * @param oidcUser The authenticated OIDC user with attributes.
     *(Check OidcUser class for more details.)
     * @return The saved or existing user.
     */
    public User processOAuth2User(OidcUser oidcUser) {
        String email = oidcUser.getAttribute("email");

        Optional<User> optionalUser = userRepository.findByEmail(email);
        User user;

        if (optionalUser.isPresent()) {
            // If user already exists
            user = optionalUser.get();

            // If authenticatedBy is null, determine its value
            if (user.getAuthenticatedBy() == null) {
                // determine assignment based on idToken's presence
                if (user.getIdToken() == null || user.getIdToken().isEmpty()) {
                    user.setAuthenticatedBy(AuthenticatedBy.LOCAL);
                } else {
                    user.setAuthenticatedBy(AuthenticatedBy.SSO);
                }
            }
        } else {
            // Create and save new user in DB
            user = new User();
            user.setEmail(email);
            user.setName(oidcUser.getAttribute("name"));
            user.setGivenName(oidcUser.getAttribute("given_name"));
            user.setFamilyName(oidcUser.getAttribute("family_name"));
            user.setPictureUrl(oidcUser.getAttribute("picture"));
            user.setAuthenticatedBy(AuthenticatedBy.SSO);
        }

        // Get new id_token and expiry values
        String newIdToken = oidcUser.getIdToken().getTokenValue();
        Instant newExpiryTime = oidcUser.getIdToken().getExpiresAt();

        boolean updated = false;

        // Update id_token and expiry time if changed
        if (!newIdToken.equals(user.getIdToken())) {
            user.setIdToken(newIdToken);
            updated = true;
        }

        if (!newExpiryTime.equals(user.getExpiryTime())) {
            user.setExpiryTime(newExpiryTime);
            updated = true;
        }

        // Save only if any fields were updated or if it is a new user
        if (updated || user.getId() == null) {
            return userRepository.save(user);
        }

        return user;
    }


}
