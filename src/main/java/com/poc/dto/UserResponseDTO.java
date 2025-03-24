package com.poc.dto;

public class UserResponseDTO {

    private String name;
    private String email;
    private String profilePic;
    private String message;
    public UserResponseDTO(String name, String email, String profilePic, String message) {
        this.name = name;
        this.email = email;
        this.profilePic = profilePic;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public String getMessage() {
        return message;
    }
}
