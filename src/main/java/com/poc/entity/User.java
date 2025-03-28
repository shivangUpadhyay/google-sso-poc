package com.poc.entity;

import com.poc.enums.AuthenticatedBy;

import javax.persistence.*;
import java.time.Instant;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String name;
    private String givenName;
    private String familyName;
    private String pictureUrl;

    @Enumerated(EnumType.STRING)
    private AuthenticatedBy authenticatedBy;
    @Column(columnDefinition = "TEXT") //Lob preferred
    private String idToken;

    private Instant expiryTime;
    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getIdToken() {
        return idToken;
    }

    public AuthenticatedBy getAuthenticatedBy() {
        return authenticatedBy;
    }

    public void setAuthenticatedBy(AuthenticatedBy authenticatedBy) {
        this.authenticatedBy = authenticatedBy;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public Instant getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Instant expiryTime) {
        this.expiryTime = expiryTime;
    }
}