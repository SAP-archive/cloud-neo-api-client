package com.sap.cloud.rest.neo.api.client.integration;

import static com.sap.cloud.rest.neo.api.client.integration.NeoApiClientIntegrationTest.getPlatformClientId;
import static com.sap.cloud.rest.neo.api.client.integration.NeoApiClientIntegrationTest.getPlatformClientSecret;
import static com.sap.cloud.rest.neo.api.client.integration.NeoApiClientIntegrationTest.getProviderApplication;
import static com.sap.cloud.rest.neo.api.client.integration.NeoApiClientIntegrationTest.getProviderSubaccount;
import static com.sap.cloud.rest.neo.api.client.integration.NeoApiClientIntegrationTest.getRegionAlias;
import static com.sap.cloud.rest.neo.api.client.integration.NeoApiClientIntegrationTest.getSubaccount;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sap.cloud.rest.api.client.auth.oauth.OAuthAuthentication;
import com.sap.cloud.rest.neo.api.client.config.NeoClientFactory;
import com.sap.cloud.rest.neo.api.client.service.authorization.management.AuthorizationManagementService;
import com.sap.cloud.rest.neo.api.client.service.authorization.management.model.Role;
import com.sap.cloud.rest.neo.api.client.service.authorization.management.model.User;
import com.sap.cloud.rest.neo.api.client.service.oauth.platform.PlatformClientsOAuthAuthenticationBuilder;

public class AuthorizationManagementServiceIntegrationTest {

    private static final String CUSTOM = "CUSTOM";
    private static final String USER_NAME = "test_user_1";
    private static final String USER_NAME_2 = "test_user_2";
    private static final String USER_NAME_3 = "test_user_3";
    private static final String ROLE_NAME = "test_role_1";
    private static final String ROLE_NAME_2 = "test_role_2";
    private static final String ROLE_NAME_3 = "test_role_3";

    private static AuthorizationManagementService authorizationManagementService;

    private static String providerSubaccount;
    private static String providerApplication;

    private static String subaccount;
    //private static String authorizationManagementHost;

    private static List<String> roleNames = Arrays.asList(ROLE_NAME, ROLE_NAME_2, ROLE_NAME_3);
    private static List<String> userNames = Arrays.asList(USER_NAME, USER_NAME_2, USER_NAME_3);
    private List<String> users = Arrays.asList(new String(USER_NAME), new String(USER_NAME_2), new String(USER_NAME_3));

    @BeforeClass
    public static void setup() {
        subaccount = getSubaccount();
        providerSubaccount = getProviderSubaccount();
        providerApplication = getProviderApplication();

        OAuthAuthentication authentication = PlatformClientsOAuthAuthenticationBuilder.getBuilder()
                .subaccount(getSubaccount())
                .regionAlias(getRegionAlias())
                .clientID(getPlatformClientId())
                .clientSecret(getPlatformClientSecret().toCharArray())
                .build();
        // Create Neo API Client Factory
        NeoClientFactory neoClientFactory = new NeoClientFactory(authentication);
        authorizationManagementService = neoClientFactory.getAuthorizationManagementService(getRegionAlias(),
                getSubaccount());

        authorizationManagementService.deleteRoles(subaccount, providerApplication, providerSubaccount,
                roleNames);
    }

    @AfterClass
    public static void cleanUp() {
        authorizationManagementService.deleteRoles(subaccount, providerApplication, providerSubaccount,
                roleNames);
    }

    @Test
    public void rolesManagingTest() throws JSONException {

        createRolesAndAssertCreation();

        deleteSingleRoleAndAssertDeletion();

        deleteRemainingRolesAndAssertDeletion();
    }

    @Test
    public void assigningUsersToRolesTest() throws JSONException {
        authorizationManagementService.createRoles(subaccount, providerApplication, providerSubaccount, roleNames);

        assignUsersToRoleAndAssertAssignment();

        unassignUserFromRoleAndAssertUnassignment();

        unassignRemainingUsersFromRoleAndAssertUnassignment();

        authorizationManagementService.deleteRoles(subaccount, providerApplication, providerSubaccount, roleNames);
    }

    @Test
    public void unassignRolesFromUserTest() throws JSONException {

        authorizationManagementService.createRoles(subaccount, providerApplication, providerSubaccount, roleNames);

        assignUserToRoles();

        unassignAllRolesFromUserAndAssertUnassignment();

        authorizationManagementService.deleteRoles(subaccount, providerApplication, providerSubaccount, roleNames);

    }

    private void createRolesAndAssertCreation() {
        List<Role> roles = authorizationManagementService.retrieveRoles(subaccount, providerApplication,
                providerSubaccount);
        int rolesCountBefore = roles.size();

        authorizationManagementService.createRoles(subaccount, providerApplication, providerSubaccount, roleNames);

        roles = authorizationManagementService.retrieveRoles(subaccount, providerApplication, providerSubaccount);
        int rolesCountAfter = roles.size();

        assertEquals("Three roles should be added", rolesCountBefore, rolesCountAfter - 3);
        assertCorrectRolesArePresent(roles, roleNames);
    }

    private void deleteSingleRoleAndAssertDeletion() {

        List<Role> roles = authorizationManagementService.retrieveRoles(subaccount, providerApplication,
                providerSubaccount);
        int rolesCountBefore = roles.size();

        authorizationManagementService.deleteRole(subaccount, providerApplication, providerSubaccount, ROLE_NAME);

        roles = authorizationManagementService.retrieveRoles(subaccount, providerApplication, providerSubaccount);
        int rolesCountAfter = roles.size();

        assertEquals("One role should be removed", rolesCountBefore, rolesCountAfter + 1);

        boolean isPresent = roles.stream().filter(role -> role.getName().equals(ROLE_NAME)).findFirst().isPresent();
        assertFalse(isPresent);

        assertCorrectRolesArePresent(roles, Arrays.asList(ROLE_NAME_2, ROLE_NAME_3));
    }

    private void deleteRemainingRolesAndAssertDeletion() {
        authorizationManagementService.deleteRoles(subaccount, providerApplication, providerSubaccount, roleNames);

        List<Role> roles = authorizationManagementService.retrieveRoles(subaccount, providerApplication,
                providerSubaccount);
        assertEquals("Created roles should be deleted", 0,
                roles.stream().filter(role -> roleNames.contains(role.getName())).count());

    }

    private void assertCorrectRolesArePresent(List<Role> rolesPresent, List<String> namesOfExpectedRoles) {
        Role roleDto;

        for (String roleName : namesOfExpectedRoles) {
            roleDto = getRoleByName(rolesPresent, roleName);
            assertRoleDtoExpectations(roleDto, roleName);
        }
    }

    private Role getRoleByName(List<Role> roles, String roleName) {
        return roles.stream().filter(role -> role.getName().equals(roleName)).findFirst().get();
    }

    private void assertRoleDtoExpectations(Role roleDto, String roleName) {
        assertEquals(roleName, roleDto.getName());
        assertEquals(CUSTOM, roleDto.getType());
        assertEquals(false, roleDto.isShared());
        assertEquals(false, roleDto.isApplicationRole());
    }

    private void assignUsersToRoleAndAssertAssignment() {

        authorizationManagementService.assignUsersToRole(subaccount, providerApplication, providerSubaccount, ROLE_NAME,
                users);

        List<User> users = authorizationManagementService.retrieveUsersAssignedToRole(subaccount, providerApplication,
                providerSubaccount, ROLE_NAME);
        assertEquals("Assigned users to role are not as expected", 3, users.size());
    }

    private void unassignUserFromRoleAndAssertUnassignment() {

        authorizationManagementService.unassignUserFromRole(subaccount, providerApplication, providerSubaccount,
                ROLE_NAME, USER_NAME);

        List<User> users = authorizationManagementService.retrieveUsersAssignedToRole(subaccount, providerApplication,
                providerSubaccount, ROLE_NAME);
        assertEquals("Two users should be still assigned", 2, users.size());
    }

    private void unassignRemainingUsersFromRoleAndAssertUnassignment() {

        authorizationManagementService.unassignUsersFromRole(subaccount, providerApplication, providerSubaccount,
                ROLE_NAME, userNames);

        List<User> users = authorizationManagementService.retrieveUsersAssignedToRole(subaccount, providerApplication,
                providerSubaccount, ROLE_NAME);
        assertEquals("All users should be unassigned from role", 0, users.size());
    }

    private void assignUserToRoles() {

        authorizationManagementService.assignUserToRole(subaccount, providerApplication, providerSubaccount, ROLE_NAME,
                USER_NAME);
        authorizationManagementService.assignUserToRole(subaccount, providerApplication, providerSubaccount,
                ROLE_NAME_2, USER_NAME);
        authorizationManagementService.assignUserToRole(subaccount, providerApplication, providerSubaccount,
                ROLE_NAME_3, USER_NAME);
    }

    private void unassignAllRolesFromUserAndAssertUnassignment() {

        for (String roleName : roleNames) {
            List<User> users = authorizationManagementService.retrieveUsersAssignedToRole(subaccount,
                    providerApplication, providerSubaccount, roleName);

            assertTrue("User should be assigned to every role", users.stream()
                    .filter(currentUser -> currentUser.getName().equals(USER_NAME)).findFirst().isPresent());
        }

        authorizationManagementService.unassignAllRolesFromUser(subaccount, USER_NAME);

        for (String roleName : roleNames) {
            List<User> users = authorizationManagementService.retrieveUsersAssignedToRole(subaccount,
                    providerApplication, providerSubaccount, roleName);

            assertFalse("User should be unassigned from every role", users.stream()
                    .filter(currentUser -> currentUser.getName().equals(USER_NAME)).findFirst().isPresent());
        }
    }

}