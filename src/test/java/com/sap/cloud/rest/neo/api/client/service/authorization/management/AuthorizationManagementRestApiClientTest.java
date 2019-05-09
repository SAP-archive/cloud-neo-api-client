package com.sap.cloud.rest.neo.api.client.service.authorization.management;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.cloud.rest.api.client.config.RestApiClientConfig;
import com.sap.cloud.rest.neo.api.client.RestApiClientAbstractTest;
import com.sap.cloud.rest.neo.api.client.service.authorization.management.dto.RetrieveRoleDto;
import com.sap.cloud.rest.neo.api.client.service.authorization.management.dto.RolesDto;
import com.sap.cloud.rest.neo.api.client.service.authorization.management.dto.UserDto;
import com.sap.cloud.rest.neo.api.client.service.authorization.management.dto.UsersDto;

public class AuthorizationManagementRestApiClientTest extends RestApiClientAbstractTest {

    private AuthorizationManagementService authorizationManagementService;

    private List<UserDto> users = Arrays.asList(new UserDto("test_user"), new UserDto("test_user2"));
    private List<String> userNames = Arrays.asList("test_user", "test_user2");
    private List<RetrieveRoleDto> responseRoles = Arrays.asList(
            new RetrieveRoleDto("test_role", "PREDEFINED", true, true),
            new RetrieveRoleDto("test_role2", "CUSTOM", false, false));
    private List<String> roleNames = Arrays.asList("test_role", "test_role2");

    @Test
    public void assignUserToRoleTest() throws Exception {
        verifyRequestMethodAndUrl(
                () -> authorizationManagementService.assignUserToRole("test_subaccount", "test_appname",
                        "test_provider_subaccount", "managerRole", "test_user"),
                "PUT",
                "https://example.com/authorization/v1/accounts/test_subaccount/apps/test_appname/roles/users?providerAccount=test_provider_subaccount&roleName=managerRole");
    }

    @Test
    public void assignUsersToRoleTest() throws Exception {
        verifyRequestMethodAndUrl(
                () -> authorizationManagementService.assignUsersToRole("test_subaccount", "test_appname",
                        "test_provider_subaccount", "managerRole", userNames),
                "PUT",
                "https://example.com/authorization/v1/accounts/test_subaccount/apps/test_appname/roles/users?providerAccount=test_provider_subaccount&roleName=managerRole");
    }

    @Test
    public void retrieveUsersAssignedToRoleTest() throws Exception {
        verifyRequestMethodAndUrlAndRequestBody(
                () -> authorizationManagementService.retrieveUsersAssignedToRole("test_subaccount", "test_appname",
                        "test_provider_subaccount", "managerRole"),
                "GET",
                "https://example.com/authorization/v1/accounts/test_subaccount/apps/test_appname/roles/users?providerAccount=test_provider_subaccount&roleName=managerRole",
                convertUsersToStringBody());

    }

    @Test
    public void unassignsUserFromRoleTest() throws Exception {
        verifyRequestMethodAndUrl(
                () -> authorizationManagementService.unassignUserFromRole("test_subaccount", "test_appname",
                        "test_provider_subaccount", "managerRole", "test_user"),
                "DELETE",
                "https://example.com/authorization/v1/accounts/test_subaccount/apps/test_appname/roles/users?users=test_user&providerAccount=test_provider_subaccount&roleName=managerRole");
    }

    @Test
    public void unassignsUsersFromRoleTest() throws Exception {
        verifyRequestMethodAndUrl(
                () -> authorizationManagementService.unassignUsersFromRole("test_subaccount", "test_appname",
                        "test_provider_subaccount", "managerRole", userNames),
                "DELETE",
                "https://example.com/authorization/v1/accounts/test_subaccount/apps/test_appname/roles/users?users=test_user;test_user2&providerAccount=test_provider_subaccount&roleName=managerRole");
    }

    @Test
    public void unassignAllRolesFromUserTest() throws Exception {
        verifyRequestMethodAndUrl(
                () -> authorizationManagementService.unassignAllRolesFromUser("test_subaccount", "test_user_id"),
                "DELETE",
                "https://example.com/authorization/v1/accounts/test_subaccount/users/roles?userId=test_user_id&roles=*");
    }

    @Test
    public void createRoleTest() throws Exception {
        verifyRequestMethodAndUrl(
                () -> authorizationManagementService.createRole("test_subaccount", "test_appname",
                        "test_provider_subaccount", "test_role"),
                "POST",
                "https://example.com/authorization/v1/accounts/test_subaccount/apps/test_appname/roles?providerAccount=test_provider_subaccount");
    }

    @Test
    public void createRolesTest() throws Exception {
        verifyRequestMethodAndUrl(
                () -> authorizationManagementService.createRoles("test_subaccount", "test_appname",
                        "test_provider_subaccount", roleNames),
                "POST",
                "https://example.com/authorization/v1/accounts/test_subaccount/apps/test_appname/roles?providerAccount=test_provider_subaccount");
    }

    @Test
    public void retrieveRolesTest() throws Exception {
        verifyRequestMethodAndUrlAndRequestBody(
                () -> authorizationManagementService.retrieveRoles("test_subaccount", "test_appname",
                        "test_provider_subaccount"),
                "GET",
                "https://example.com/authorization/v1/accounts/test_subaccount/apps/test_appname/roles?providerAccount=test_provider_subaccount",
                convertRolesToStringBody());
    }

    @Test
    public void deleteRoleTest() throws Exception {
        verifyRequestMethodAndUrl(
                () -> authorizationManagementService.deleteRole("test_subaccount", "test_appname",
                        "test_provider_subaccount", "test_role"),
                "DELETE",
                "https://example.com/authorization/v1/accounts/test_subaccount/apps/test_appname/roles?roles=test_role&providerAccount=test_provider_subaccount");
    }

    @Test
    public void deleteRolesTest() throws Exception {
        verifyRequestMethodAndUrl(
                () -> authorizationManagementService.deleteRoles("test_subaccount", "test_appname",
                        "test_provider_subaccount", roleNames),
                "DELETE",
                "https://example.com/authorization/v1/accounts/test_subaccount/apps/test_appname/roles?roles=test_role;test_role2&providerAccount=test_provider_subaccount");
    }

    @Override
    public void initService() {
        RestApiClientConfig config = new RestApiClientConfig(VALID_HOST);
        authorizationManagementService = new AuthorizationManagementRestApiClient(config, httpClientProvider);
    }

    @Override
    public void executeSampleRequest() throws Exception {
        authorizationManagementService.createRoles("test-subaccount", "test-appname", "provider-account", roleNames);
    }

    private String convertUsersToStringBody() throws Exception {
        UsersDto usersDto = new UsersDto(users);
        return convertObjectToString(usersDto);
    }

    private String convertRolesToStringBody() throws Exception {
        RolesDto<RetrieveRoleDto> roles = new RolesDto<RetrieveRoleDto>(responseRoles);
        return convertObjectToString(roles);
    }

    private String convertObjectToString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

}
