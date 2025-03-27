package com.poc.config;

import com.poc.service.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;


/* Login URL:
http://localhost:8080/oauth2/authorization/google (redirected from localhost:8080/login) */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private OAuth2UserService<OidcUserRequest, OidcUser> customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/login", "/login-failure", "/error", "/oauth2/**", "/logout-success").permitAll()
                .antMatchers("/session-info").authenticated()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .defaultSuccessUrl("/login-success", true) // Redirect after successful login
                .failureUrl("/login-failure") // Redirect on failure
                .userInfoEndpoint()
                .oidcUserService(customOAuth2UserService) // Custom Service
                .and()
                .and()
                .logout()
                .logoutUrl("/logout") // Logout URL
                .logoutSuccessUrl("/logout-success")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()
                .csrf()
                .ignoringAntMatchers("/login", "/logout", "/error", "/login/oauth2/code/google"); // Ignore CSRF for OAuth2 and other relevant endpoints
    }
}


// TODO: bcrypt removed for now, add later for user info storage.

