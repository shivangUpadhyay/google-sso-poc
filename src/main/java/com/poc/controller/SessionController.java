package com.poc.controller;

import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/session-info")
public class SessionController {

    private final SessionRegistry sessionRegistry;

    public SessionController(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }

    @GetMapping
    public Map<String, Object> getActiveSessions() {
        List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
        Map<String, Object> sessionDetails = new HashMap<>();

        for (Object principal : allPrincipals) {
            List<SessionInformation> sessionInfoList = sessionRegistry.getAllSessions(principal, false);
            sessionDetails.put(principal.toString(), sessionInfoList.size());
        }

        return sessionDetails;
    }
}
