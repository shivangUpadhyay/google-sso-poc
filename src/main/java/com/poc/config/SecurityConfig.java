package com.poc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import java.util.Map;

/* Login URL:
http://localhost:8080/oauth2/authorization/google (redirected from localhost:8080/login) */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/login", "/login-failure", "/error", "/oauth2/**", "/logout-success").permitAll()
                .antMatchers("/session-info").authenticated()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
               // .loginPage("/login") // Custom login page (optional)
                .defaultSuccessUrl("/login-success", true) // Redirect on successful login
                .failureUrl("/login-failure") // Redirect if login fails
                .userInfoEndpoint()
                //.userService(oauth2UserService())
                .and()
                .and()
                .logout()
                .logoutUrl("/logout") // Explicitly setting logout URL
                .logoutSuccessUrl("/logout-success")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()
                .csrf()
                .ignoringAntMatchers("/login", "/logout", "/error", "/login/oauth2/code/google") // Ignore CSRF for OAuth2 endpoints
                .and()
                .sessionManagement()
                .maximumSessions(5) // Allow max 5 active sessions at a time
                .maxSessionsPreventsLogin(false) // False: if limit reached, allows new login & expire old sessions.
                                                 // True : if limit reached, deny new login.
                .sessionRegistry(sessionRegistry()); //keep track of active sessions
    }

/*
    @Bean
    public DefaultOAuth2UserService oauth2UserService() {
        return new DefaultOAuth2UserService() {
            @Override
            public OAuth2User loadUser(OAuth2UserRequest userRequest) {
                OAuth2User oAuth2User = super.loadUser(userRequest);
                Map<String, Object> attributes = oAuth2User.getAttributes();

                // Extract Google user information
                String email = (String) attributes.get("email");
                String name = (String) attributes.get("name");

                // Return authenticated OAuth2User with extracted attributes
                return oAuth2User;
            }
        };
    }
*/

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

// TODO: bcrypt removed for now, add later for user info storage.
}
