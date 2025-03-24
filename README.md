# google-sso-poc
A pure Java/Spring Boot POC (Proof of Concept) showcasing the working of Google Single Sign-On (SSO) with OAuth2.

## Configuration Before Running
- Rename "application.properties.template" to "application.properties" in src\main\resources .
- Inside the newly renamed application.properties file, replace the following with your own Google credentials:
-> spring.security.oauth2.client.registration.google.client-id=YOUR_CLIENT_ID
  
-> spring.security.oauth2.client.registration.google.client-secret=YOUR_CLIENT_SECRET 

## Technologies Used
- Java 11
- Spring Boot 2.3.1 RELEASE
- Spring Security
- OAuth2 / Google SSO
- Gradle

## URLS
- GET: localhost:8080/login -> http://localhost:8080/oauth2/authorization/google
- POST: localhost:8080/logout
- GET: localhost:8080/session-info

## Future Releases
- Implementing BCrypt for user data storage.
- Role-based authorization.
