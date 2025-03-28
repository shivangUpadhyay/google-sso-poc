# google-sso-poc
A pure Java/Spring Boot POC (Proof of Concept) showcasing the working of Google Single Sign-On (SSO) with OAuth2.

## Configuration Before Running
- Rename "application.properties.template" to `application.properties` (in `src\main\resources`).
- Inside the newly renamed `application.properties` file, replace the following with your own Google credentials:
  - spring.security.oauth2.client.registration.google.client-id=`YOUR_CLIENT_ID`
  - spring.security.oauth2.client.registration.google.client-secret=`YOUR_CLIENT_SECRET`
- Add your own MySQL credentials inside `application.properties`:
  - spring.datasource.username=`INSERT_USERNAME_HERE`
  - spring.datasource.password=`INSERT_PASSWORD_HERE`


### Technologies Used
- Java 11
- Spring Boot 2.3.1 RELEASE
- Spring Security
- OAuth2 / Google SSO
- Gradle 7.6.1
- MySQL 8.0.41