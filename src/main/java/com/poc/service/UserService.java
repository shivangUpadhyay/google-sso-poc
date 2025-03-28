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
     * Processes the authenticated OIDC user and updates/creates the corresponding user record in the database.
     *
     * Workflow:
     * 1. Checks if the user already exists in the database.
     * 2. If the user exists:
     *    - Checks if `authenticatedBy` is null.
     *    - If null, assigns a value based on existing `idToken`:
     *        - `LOCAL` if `idToken` is null or empty (user was created locally).
     *        - `SSO` if `idToken` is present (user logged in via SSO previously).
     *    - Updates `idToken` and `expiryTime` if they have changed.
     * 3. If the user does not exist:
     *    - Creates a new user with attributes from the OIDC user.
     *    - Assigns `authenticatedBy` to `SSO` for new SSO users.
     * 4. Saves the user only if changes were made or if the user is newly created.
     *
     * @param oidcUser The authenticated OIDC user with attributes.
     *                 (Check OidcUser class for available attributes like email, name, etc.)
     * @return The saved or existing user with updated attributes.
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
