package com.sap.cloud.rest.neo.api.client.service.authorization.management;

import java.util.List;

import com.sap.cloud.rest.neo.api.client.service.authorization.management.model.Role;
import com.sap.cloud.rest.neo.api.client.service.authorization.management.model.User;

/**
 * Represent the Authorization Management API.
 *
 */
public interface AuthorizationManagementService {

    /**
     * Assigns a user to the specified role.
     * 
     * @param accountName
     *            subaccount name of the subscribed application
     * @param appName
     *            application name in which user to be assigned to a role
     * @param providerAccount
     *            provider subaccount of the application
     * @param roleName
     *            specified role name
     * @param userName
     *            user to assign
     */
    void assignUserToRole(String accountName, String appName, String providerAccount, String roleName, String userName);

    /**
     * Assigns the users to the specified role in the specified account and
     * application.
     * 
     * @param accountName
     *            subaccount name of the subscribed application
     * @param appName
     *            application name in which user to be assigned to a role
     * @param providerAccount
     *            provider subaccount of the application
     * @param roleName
     *            specified role name
     * @param userNames
     *            users to assign
     */
    void assignUsersToRole(String accountName, String appName, String providerAccount, String roleName,
            List<String> userNames);

    /**
     * Returns users assigned to the specified role in the specified account and
     * application.
     * 
     * @param accountName
     *            subaccount name of the subscribed application
     * @param appName
     *            application name in which user to be assigned to a role
     * @param providerAccount
     *            provider subaccount of the application
     * @param roleName
     *            specified role name
     * @return list of assigned users to the role
     */
    List<User> retrieveUsersAssignedToRole(String accountName, String appName, String providerAccount, String roleName);

    /**
     * Unassigns a user from the specified role in the specified account and
     * application.
     * 
     * @param accountName
     *            subaccount name of the subscribed application
     * @param appName
     *            application name in which user to be assigned to a role
     * @param providerAccount
     *            provider subaccount of the application
     * @param roleName
     *            specified role name
     * @param userName
     *            user to unassign
     */
    void unassignUserFromRole(String accountName, String appName, String providerAccount, String roleName,
            String userName);

    /**
     * Unassigns users from the specified role in the specified account and
     * application.
     * 
     * @param accountName
     *            subaccount name of the subscribed application
     * @param appName
     *            application name in which user to be assigned to a role
     * @param providerAccount
     *            provider subaccount of the application
     * @param roleName
     *            specified role name
     * @param userNames
     *            users to unassign
     */
    void unassignUsersFromRole(String accountName, String appName, String providerAccount, String roleName,
            List<String> userNames);

    /**
     * Unassigns all roles assigned to the specified user
     * 
     * @param accountName
     *            subaccount name of the subscribed application
     * @param userName
     *            user, whose roles are to be unassigned
     */
    void unassignAllRolesFromUser(String accountName, String userName);

    /**
     * Creates a custom role in the specified account and application.
     * 
     * @param accountName
     *            subaccount name of the subscribed application
     * @param appName
     *            application name in which user to be assigned to a role
     * @param providerAccount
     *            provider subaccount of the application
     * @param roleName
     *            specified role name
     */
    void createRole(String accountName, String appName, String providerAccount, String roleName);

    /**
     * Creates custom roles in the specified account and application.
     * 
     * @param accountName
     *            subaccount name of the subscribed application
     * @param appName
     *            application name in which user to be assigned to a role
     * @param providerAccount
     *            provider subaccount of the application
     * @param roles
     *            specified roles
     */
    void createRoles(String accountName, String appName, String providerAccount, List<String> roles);

    /**
     * Returns the list of the roles in the specified account and application.
     * 
     * @param accountName
     *            subaccount name of the subscribed application
     * @param appName
     *            application name in which user to be assigned to a role
     * @param providerAccount
     *            provider subaccount of the application
     * @return list of retrieved roles
     */
    List<Role> retrieveRoles(String accountName, String appName, String providerAccount);

    /**
     * Deletes a custom role in the specified account and application.
     * 
     * @param accountName
     *            subaccount name of the subscribed application
     * @param appName
     *            application name in which user to be assigned to a role
     * @param providerAccount
     *            provider subaccount of the application
     * @param roleName
     *            specified role name
     */
    void deleteRole(String accountName, String appName, String providerAccount, String roleName);

    /**
     * Deletes custom roles in the specified account and application.
     * 
     * @param accountName
     *            subaccount name of the subscribed application
     * @param appName
     *            application name in which user to be assigned to a role
     * @param providerAccount
     *            provider subaccount of the application
     * @param roles
     *            specified roles
     */
    void deleteRoles(String accountName, String appName, String providerAccount, List<String> roles);

}
