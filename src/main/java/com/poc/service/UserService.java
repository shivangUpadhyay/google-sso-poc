package com.poc.service;

import org.springframework.stereotype.Service;
import com.poc.entity.User;
import com.poc.repository.UserRepository;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Checks if the user exists in the database.
     * If not, create and save a new user.
     *
     * @param oidcUser The authenticated OIDC user with attributes.
     * (Check OidcUser class for more details.)
     * @return The saved or existing user.
     */
    public User processOAuth2User(OidcUser oidcUser) {
        String email = oidcUser.getAttribute("email");

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            // User already exists, return the existing user
            return optionalUser.get();
        } else {
            // Create and save new user in DB
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setName(oidcUser.getAttribute("name"));
            newUser.setGivenName(oidcUser.getAttribute("given_name"));
            newUser.setFamilyName(oidcUser.getAttribute("family_name"));
            newUser.setPictureUrl(oidcUser.getAttribute("picture"));

            return userRepository.save(newUser);
        }
    }
}
