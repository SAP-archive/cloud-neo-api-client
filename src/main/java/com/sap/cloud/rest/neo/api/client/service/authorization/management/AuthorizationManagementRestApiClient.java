package com.sap.cloud.rest.neo.api.client.service.authorization.management;

import static java.util.Arrays.asList;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sap.cloud.rest.api.client.RequestBuilder;
import com.sap.cloud.rest.api.client.RestApiClient;
import com.sap.cloud.rest.api.client.config.RestApiClientConfig;
import com.sap.cloud.rest.api.client.http.HttpClientProvider;
import com.sap.cloud.rest.api.client.model.Request;
import com.sap.cloud.rest.api.client.utils.JacksonJsonResponseHandler;
import com.sap.cloud.rest.neo.api.client.service.authorization.management.dto.CreateRoleDto;
import com.sap.cloud.rest.neo.api.client.service.authorization.management.dto.RetrieveRoleDto;
import com.sap.cloud.rest.neo.api.client.service.authorization.management.dto.RolesDto;
import com.sap.cloud.rest.neo.api.client.service.authorization.management.dto.UserDto;
import com.sap.cloud.rest.neo.api.client.service.authorization.management.dto.UsersDto;
import com.sap.cloud.rest.neo.api.client.service.authorization.management.model.Role;
import com.sap.cloud.rest.neo.api.client.service.authorization.management.model.User;

/**
 * Implements the {@link AuthorizationManagementService} by making HTTP requests
 * to Authorization Management API on SAP CP. This is the default used
 * implementation.
 *
 */
public class AuthorizationManagementRestApiClient extends RestApiClient implements AuthorizationManagementService {

    private static final String BASE_API_PATH = "/authorization/v1";
    private static final String ROLES_PATH = "/accounts/{0}/apps/{1}/roles";
    private static final String ROLES_USERS_PATH = ROLES_PATH + "/users";

    private static final String USERS_PATH = "/accounts/{0}/users";
    private static final String USERS_ROLES_PATH = USERS_PATH + "/roles";

    private static final String ROLES = "roles";
    private static final String USERS = "users";
    private static final String ROLE_NAME = "roleName";
    private static final String PROVIDER_ACCOUNT = "providerAccount";
    private static final String USER_ID = "userId";
    private static final String ALL_ROLES_SYMBOL = "*";

    private static final String ASSIGNING_USERS_TO_ROLE_WAS_SUCCESSFUL_MSG = "Assigning users [{}] to role [{}] was successful, account [{}], application [{}], provider account [{}]";
    private static final String RETRIEVING_USERS_ASSIGNED_TO_ROLE_WAS_SUCCESSFUL_MSG = "Retrieving users assigned to role [{}] was successful, account [{}], application [{}], provider account [{}]: [{}]";
    private static final String UNASSIGNING_USERS_FROM_ROLE_WAS_SUCCESSFUL_MSG = "Unassigning users [{}] from role [{}] was successful, account [{}], application [{}], provider account [{}]";

    private static final String UNASSIGNING_ALL_ROLES_FROM_USER_WAS_SUCCESSFUL_MSG = "Unassigning roles from user [{}] was successful, account [{}]";

    private static final String CREATING_ROLES_WAS_SUCCESSFUL_MSG = "Creating roles [{}] was successful, account [{}], application [{}], provider account [{}]";
    private static final String RETRIEVING_ROLES_WAS_SUCCESSFUL_MSG = "Retrieving roles was successful, account [{}], application [{}], provider account [{}]: [{}]";
    private static final String DELETING_ROLES_WAS_SUCCESSFUL_MSG = "Deleting roles [{}] was successful, account [{}], application [{}], provider account [{}]";

    private static final Logger log = LoggerFactory.getLogger(AuthorizationManagementRestApiClient.class);

    /**
     * Creates a {@link AuthorizationManagementRestApiClient} instance.
     * 
     * @param config {@link RestApiClientConfig} client configuration.
     */
    public AuthorizationManagementRestApiClient(RestApiClientConfig config) {
        super(config);
    }

    /**
     * A constructor which allows providing a custom {@link HttpClientProvider}.
     * 
     * @param config {@link RestApiClientConfig} client configuration.
     * @param httpClientProvider
     *            the HTTP client provider to be used to create HTTP clients.
     */
    public AuthorizationManagementRestApiClient(RestApiClientConfig config, HttpClientProvider httpClientProvider) {
        super(config, httpClientProvider);
    }

    @Override
    protected String getApiPath() {
        return BASE_API_PATH;
    }

    @Override
    public void assignUserToRole(String accountName, String appName, String providerAccount, String roleName,
            String userDto) {
        assignUsersToRole(accountName, appName, providerAccount, roleName, asList(userDto));
    }

    @Override
    public void assignUsersToRole(String accountName, String appName, String providerAccount, String roleName,
            List<String> userNames) {
        Request<UsersDto> assignUsersRequest = buildUserRequest(RequestBuilder
                .putRequest(UsersDto.class)
                .uri(buildRequestUri(ROLES_USERS_PATH, accountName, appName))
                .entity(mapToUsersDto(userNames)),
                providerAccount, roleName);

        execute(assignUsersRequest);

        log.info(ASSIGNING_USERS_TO_ROLE_WAS_SUCCESSFUL_MSG, userNames, roleName, accountName, appName,
                providerAccount);
    }

    @Override
    public List<User> retrieveUsersAssignedToRole(String accountName, String appName, String providerAccount,
            String roleName) {
        Request<String> retrieveUsersAssignedToRoleRequest = buildUserRequest(RequestBuilder
                .getRequest()
                .uri(buildRequestUri(ROLES_USERS_PATH, accountName, appName)),
                providerAccount, roleName);

        UsersDto usersDto = execute(retrieveUsersAssignedToRoleRequest,
                new JacksonJsonResponseHandler<UsersDto>(UsersDto.class)).getEntity();

        List<User> usersList = mapToUserList(usersDto);

        log.info(RETRIEVING_USERS_ASSIGNED_TO_ROLE_WAS_SUCCESSFUL_MSG, roleName, accountName, appName, providerAccount,
                usersList);

        return usersList;
    }

    @Override
    public void unassignUserFromRole(String accountName, String appName, String providerAccount, String roleName,
            String userName) {
        unassignUsersFromRole(accountName, appName, providerAccount, roleName, asList(userName));
    }

    @Override
    public void unassignUsersFromRole(String accountName, String appName, String providerAccount, String roleName,
            List<String> userNames) {
        Request<String> unassignUsersRequest = buildUnassignUsersRequest(RequestBuilder
                .deleteRequest()
                .uri(buildRequestUri(ROLES_USERS_PATH, accountName, appName)),
                providerAccount, roleName, userNames);

        execute(unassignUsersRequest);

        log.info(UNASSIGNING_USERS_FROM_ROLE_WAS_SUCCESSFUL_MSG, userNames, roleName, accountName, appName,
                providerAccount);
    }

    @Override
    public void unassignAllRolesFromUser(String accountName, String userName) {

        Request<String> unassignUsersRequest = buildUnassignRolesRequest(RequestBuilder
                .deleteRequest()
                .uri(buildRequestUri(USERS_ROLES_PATH, accountName)), userName);

        execute(unassignUsersRequest);

        log.info(UNASSIGNING_ALL_ROLES_FROM_USER_WAS_SUCCESSFUL_MSG, userName, accountName);
    }

    @Override
    public void createRole(String accountName, String appName, String providerAccount, String roleName) {
        createRoles(accountName, appName, providerAccount, asList(roleName));
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void createRoles(String accountName, String appName, String providerAccount, List<String> roles) {
        Request<RolesDto> createRolesRequest = buildRolesRequest(RequestBuilder
                .postRequest(RolesDto.class)
                .uri(buildRequestUri(ROLES_PATH, accountName, appName))
                .entity(mapToCreateRolesDto(roles)),
                providerAccount);

        execute(createRolesRequest);

        log.info(CREATING_ROLES_WAS_SUCCESSFUL_MSG, roles, accountName, appName, providerAccount);
    }

    @Override
    public List<Role> retrieveRoles(String accountName, String appName, String providerAccount) {
        Request<String> retrieveRolesRequest = buildRolesRequest(RequestBuilder
                .getRequest()
                .uri(buildRequestUri(ROLES_PATH, accountName, appName)), providerAccount);

        TypeReference<RolesDto<RetrieveRoleDto>> rolesDtoType = new TypeReference<RolesDto<RetrieveRoleDto>>() {
        };
        RolesDto<RetrieveRoleDto> rolesDto = execute(retrieveRolesRequest,
                new JacksonJsonResponseHandler<>(rolesDtoType)).getEntity();

        List<Role> rolesList = mapToRolesList(rolesDto);

        log.info(RETRIEVING_ROLES_WAS_SUCCESSFUL_MSG, accountName, appName, providerAccount, rolesList);

        return rolesList;
    }

    private List<Role> mapToRolesList(RolesDto<RetrieveRoleDto> rolesDto) {
        return rolesDto.getRolesList().stream().map(roleDto -> new Role(roleDto.getName(), roleDto.getType(),
                roleDto.isApplicationRole(), roleDto.isShared())).collect(Collectors.toList());
    }

    @Override
    public void deleteRole(String accountName, String appName, String providerAccount, String roleName) {
        deleteRoles(accountName, appName, providerAccount, asList(roleName));
    }

    @Override
    public void deleteRoles(String accountName, String appName, String providerAccount, List<String> roleNames) {
        Request<String> deleteRolesRequest = buildDeleteRolesRequest(RequestBuilder
                .deleteRequest()
                .uri(buildRequestUri(ROLES_PATH, accountName, appName)), providerAccount,
                roleNames);

        execute(deleteRolesRequest);

        log.info(DELETING_ROLES_WAS_SUCCESSFUL_MSG, roleNames, accountName, appName, providerAccount);
    }

    private UsersDto mapToUsersDto(List<String> userNames) {
        List<UserDto> users = userNames.stream().map(name -> new UserDto(name)).collect(Collectors.toList());
        return new UsersDto(users);
    }

    private RolesDto<CreateRoleDto> mapToCreateRolesDto(List<String> rolesNames) {
        List<CreateRoleDto> roles = rolesNames.stream().map(name -> new CreateRoleDto(name))
                .collect(Collectors.toList());
        return new RolesDto<CreateRoleDto>(roles);
    }

    private List<User> mapToUserList(UsersDto usersDto) {
        return usersDto.getUsersList().stream().map(userDto -> new User(userDto.getName()))
                .collect(Collectors.toList());
    }

    private <T> Request<T> buildUserRequest(RequestBuilder<T> builder, String providerAccount, String role) {
        builder.addParameter(PROVIDER_ACCOUNT, providerAccount);
        builder.addParameter(ROLE_NAME, role);

        return builder.build();
    }

    private <T> Request<T> buildRolesRequest(RequestBuilder<T> builder, String providerAccount) {
        builder.addParameter(PROVIDER_ACCOUNT, providerAccount);

        return builder.build();
    }

    private <T> Request<T> buildDeleteRolesRequest(RequestBuilder<T> builder, String providerAccount,
            List<String> roleNames) {
        addQueryParameterList(builder, ROLES, roleNames);
        return buildRolesRequest(builder, providerAccount);
    }

    private <T> Request<T> buildUnassignUsersRequest(RequestBuilder<T> builder, String providerAccount, String role,
            List<String> userNames) {
        addQueryParameterList(builder, USERS, userNames);
        return buildUserRequest(builder, providerAccount, role);
    }

    private <T> Request<T> buildUnassignRolesRequest(RequestBuilder<T> builder, String userName) {
        builder.addParameter(USER_ID, userName);
        builder.addParameter(ROLES, ALL_ROLES_SYMBOL);

        return builder.build();
    }

    private <T> void addQueryParameterList(RequestBuilder<T> builder, String key, List<String> parameters) {
        builder.addParameter(key, StringUtils.join(parameters, ";"));
    }

}
