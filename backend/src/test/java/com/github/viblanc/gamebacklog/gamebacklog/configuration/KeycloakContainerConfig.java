package com.github.viblanc.gamebacklog.gamebacklog.configuration;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import jakarta.ws.rs.core.Response;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;

@TestConfiguration(proxyBeanMethods = false)
public class KeycloakContainerConfig {
    static final String KEYCLOAK_IMAGE = "quay.io/keycloak/keycloak:24.0.2";
    static final String REALM_NAME = "game-backlog";

    @Bean
    public KeycloakContainer keycloak(DynamicPropertyRegistry registry) {
        KeycloakContainer keycloak = new KeycloakContainer(KEYCLOAK_IMAGE)
                .withRealmImportFile("keycloak/realm.json");
        keycloak.start();
        createTestUser(keycloak);
        registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri",
                () -> keycloak.getAuthServerUrl() + "/realms/" + REALM_NAME);
        registry.add("spring.security.oauth2.resourceserver.jwt.jwk-set-uri",
                () -> keycloak.getAuthServerUrl() + "/realms/" + REALM_NAME + "/protocol/openid-connect/certs");
        registry.add("jwt.auth.converter.resource-id", () -> "gamebacklog-app");
        registry.add("jwt.auth.converter.principal-attribute", () -> "preferred_username");
        return keycloak;
    }

    private void createTestUser(KeycloakContainer keycloak) {
        // Create Keycloak admin client
        Keycloak keycloakAdmin = KeycloakBuilder.builder()
                .serverUrl(keycloak.getAuthServerUrl())
                .realm("master")
                .clientId("admin-cli")
                .username(keycloak.getAdminUsername())
                .password(keycloak.getAdminPassword())
                .grantType(OAuth2Constants.PASSWORD)
                .build();

        // Create our test user
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername("test_user");
        user.setEmail("test@mail");

        // Retrieve realm and realm's users
        RealmResource realm = keycloakAdmin.realm(REALM_NAME);
        UsersResource users = realm.users();

        if (!users.search("test_user").contains(user)) {
            // Add user to the realm
            Response response = users.create(user);
            String userId = CreatedResponseUtil.getCreatedId(response);

            // Create a password for our user
            CredentialRepresentation passwordCred = new CredentialRepresentation();
            passwordCred.setTemporary(false);
            passwordCred.setType(CredentialRepresentation.PASSWORD);
            passwordCred.setValue("test_password");

            // Change user's password
            UserResource userResource = users.get(userId);
            userResource.resetPassword(passwordCred);
        }
    }
}
