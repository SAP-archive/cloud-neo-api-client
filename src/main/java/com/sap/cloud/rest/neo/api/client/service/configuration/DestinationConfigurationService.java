package com.sap.cloud.rest.neo.api.client.service.configuration;

import com.sap.cloud.rest.api.client.exceptions.UnauthorizedException;
import com.sap.cloud.rest.neo.api.client.service.configuration.exceptions.DestinationNotFoundException;
import com.sap.cloud.rest.neo.api.client.service.configuration.exceptions.PermissionDeniedException;
import com.sap.cloud.rest.neo.api.client.service.configuration.model.BasicDestination;
import com.sap.cloud.rest.neo.api.client.service.configuration.model.Destination;
import com.sap.cloud.rest.neo.api.client.service.configuration.model.SAMLBearerDestination;

/**
 * Represent the SAP CP Configuration service.
 *
 */
public interface DestinationConfigurationService {

    /**
     * Create or update a destination in the given subaccount.
     * 
     * @param subaccountId
     *            - the subaccount id of an application for which a destination
     *            will be created.
     * @param destination
     *            - the destination to be created or updated.
     *
     * @throws UnauthorizedException
     *             - if token is null, or if the subaccount id is null or not
     *             valid.
     * @throws PermissionDeniedException
     *             - if the required user scope manageDestination is missing.
     */
    void upsertDestinationOnTenantLevel(String subaccountId, Destination destination);

    /**
     * Retrieve a destination that is defined for the given subaccount.
     * 
     * @param subaccountId
     *            - the subaccount id of an application for which a destination
     *            will be retrieved.
     * @param destinationName
     *            - the name of the destination to be retrieved.
     * 
     * @return the retrieved destination
     * 
     * @throws UnauthorizedException
     *             - if token is null, or if the subaccount id is null or not
     *             valid.
     * @throws PermissionDeniedException
     *             - if the required user scope manageDestination is missing.
     * @throws DestinationNotFoundException
     *             - if the destination is not found.
     * 
     */
    Destination retrieveDestinationOnTenantLevel(String subaccountId, String destinationName);

    /**
     * Retrieve a destination with OAuth2SAMLBearerAssertion authentication type
     * that is defined for the given subaccount.
     * 
     * @param subaccountId
     *            - the subaccount id of an application for which a destination
     *            will be retrieved.
     * @param destinationName
     *            - the name of the destination to be retrieved.
     * 
     * @return the retrieved destination with OAuth2SAMLBearerAssertion
     *         authentication type.
     * 
     * @throws UnauthorizedException
     *             - if token is null, or if the subaccount id is null or not
     *             valid.
     * @throws PermissionDeniedException
     *             - if the required user scope manageDestination is missing.
     * @throws DestinationNotFoundException
     *             - if the destination is not found.
     * 
     */
    SAMLBearerDestination retrieveSAMLBearerDestinationOnTenantLevel(String subaccountId, String destinationName);

    /**
     * Retrieve a destination with Basic authentication type that is defined for
     * the given subaccount.
     * 
     * @param subaccountId
     *            - the subaccount id of an application for which a destination
     *            will be retrieved.
     * @param destinationName
     *            - the name of the destination to be retrieved.
     * 
     * @return the retrieved destination with Basic authentication type.
     * 
     * @throws UnauthorizedException
     *             - if token is null, or if the subaccount id is null or not
     *             valid.
     * @throws PermissionDeniedException
     *             - if the required user scope manageDestination is missing.
     * @throws DestinationNotFoundException
     *             - if the destination is not found.
     * 
     */
    BasicDestination retrieveBasicDestinationOnTenantLevel(String subaccountId, String destinationName);

    /**
     * Delete a destination that is defined for the given subaccount.
     * 
     * @param subaccountId
     *            - the subaccount id of an application for which a destination
     *            will be deleted.
     * @param destinationName
     *            - the name of the destination to be deleted.
     * 
     * @throws UnauthorizedException
     *             - if token is null, or if the subaccount id is null or not
     *             valid.
     * @throws PermissionDeniedException
     *             - if the required user scope manageDestination is missing.
     * @throws DestinationNotFoundException
     *             - if the destination is not found.
     */
    void deleteDestinationOnTenantLevel(String subaccountId, String destinationName);

    /**
     * Create or update a destination in the given application and subaccount.
     * 
     * @param subaccountId
     *            - the subaccount id of an application for which a destination
     *            will be created.
     * @param applicationName
     *            - the name of the application for which a destination will be
     *            created.
     * @param destination
     *            - the destination to be created or updated.
     *
     * @throws UnauthorizedException
     *             - if token is null, or if the subaccount id is null or not
     *             valid.
     * @throws PermissionDeniedException
     *             - if the required user scope manageDestination is missing.
     */
    void upsertDestinationOnApplicationLevel(String subaccountId, String applicationName,
            Destination destination);

    /**
     * Retrieve a destination that is defined for the given application and
     * subaccount.
     * 
     * @param subaccountId
     *            - the subaccount id of an application for which a destination
     *            will be retrieved.
     * @param applicationName
     *            - the name of the application for which a destination will be
     *            retrieved.
     * @param destinationName
     *            - the name of the destination to be retrieved.
     * 
     * @return the retrieved destination
     * 
     * @throws UnauthorizedException
     *             - if token is null, or if the subaccount id is null or not
     *             valid.
     * @throws PermissionDeniedException
     *             - if the required user scope manageDestination is missing.
     * @throws DestinationNotFoundException
     *             - if the destination is not found.
     * 
     */
    Destination retrieveDestinationOnApplicationLevel(String subaccountId, String applicationName,
            String destinationName);

    /**
     * Retrieve a destination with OAuth2SAMLBearerAssertion authentication type
     * that is defined for the given application and subaccount.
     * 
     * @param subaccountId
     *            - the subaccount id of an application for which a destination
     *            will be retrieved.
     * @param applicationName
     *            - the name of the application for which a destination will be
     *            retrieved.
     * @param destinationName
     *            - the name of the destination to be retrieved.
     * 
     * @return the retrieved destination with OAuth2SAMLBearerAssertion
     *         authentication type.
     * 
     * @throws UnauthorizedException
     *             - if token is null, or if the subaccount id is null or not
     *             valid.
     * @throws PermissionDeniedException
     *             - if the required user scope manageDestination is missing.
     * @throws DestinationNotFoundException
     *             - if the destination is not found.
     * 
     */
    SAMLBearerDestination retrieveSAMLBearerDestinationOnApplicationLevel(
            String subaccountId, String applicationName, String destinationName);

    /**
     * Retrieve a destination with Basic authentication type that is defined for
     * the given application and subaccount.
     * 
     * @param subaccountId
     *            - the subaccount id of an application for which a destination
     *            will be retrieved.
     * @param applicationName
     *            - the name of the application for which a destination will be
     *            retrieved.
     * @param destinationName
     *            - the name of the destination to be retrieved.
     * 
     * @return the retrieved destination with Basic authentication type.
     * 
     * @throws UnauthorizedException
     *             - if token is null, or if the subaccount id is null or not
     *             valid.
     * @throws PermissionDeniedException
     *             - if the required user scope manageDestination is missing.
     * @throws DestinationNotFoundException
     *             - if the destination is not found.
     * 
     */
    BasicDestination retrieveBasicDestinationOnApplicationLevel(String subaccountId, String applicationName,
            String destinationName);

    /**
     * Delete a destination that is defined for the given application and
     * subaccount.
     * 
     * @param subaccountId
     *            - the subaccount id of an application for which a destination
     *            will be deleted.
     * @param applicationName
     *            - the name of the application for which a destination will be
     *            deleted.
     * @param destinationName
     *            - the name of the destination to be deleted.
     * 
     * @throws UnauthorizedException
     *             - if token is null, or if the subaccount id is null or not
     *             valid.
     * @throws PermissionDeniedException
     *             - if the required user scope manageDestination is missing.
     * @throws DestinationNotFoundException
     *             - if the destination is not found.
     */
    void deleteDestinationOnApplicationLevel(String subaccountId, String applicationName, String destinationName);

    /**
     * Create or update a destination in an application in the given provider
     * subaccount and a consumer subaccount subscribed to that application.
     * 
     * @param consumerSubaccountId
     *            - the id of the subaccount which is subscribed to the
     *            application for which a destination will be created/updated.
     * @param providerSubaccountId
     *            - the subaccount id of the application for which a destination
     *            will be created/updated.
     * @param applicationName
     *            - the name of the application for which a destination will be
     *            created.
     * @param destination
     *            - the destination to be created or updated.
     *
     * @throws UnauthorizedException
     *             - if token is null, or if the subaccount id is null or not
     *             valid.
     * @throws PermissionDeniedException
     *             - if the required user scope manageDestination is missing.
     */
    void upsertDestinationOnSubscriptionLevel(String consumerSubaccountId,
            String providerSubaccountId, String applicationName, Destination destination);

    /**
     * Retrieve a destination that is defined for an application in the given
     * provider subaccount and a consumer subaccount subscribed to that
     * application.
     * 
     * @param consumerSubaccountId
     *            - the id of the subaccount which is subscribed to the
     *            application for which a destination will be retrieved.
     * @param providerSubaccountId
     *            - the subaccount id of the application for which a destination
     *            will be retrieved.
     * @param applicationName
     *            - the name of the application for which a destination will be
     *            retrieved.
     * @param destinationName
     *            - the name of the destination to be retrieved.
     * 
     * @return the retrieved destination
     *
     * @throws UnauthorizedException
     *             - if token is null, or if the subaccount id is null or not
     *             valid.
     * @throws PermissionDeniedException
     *             - if the required user scope manageDestination is missing.
     * @throws DestinationNotFoundException
     *             - if the destination is not found.
     */
    Destination retrieveDestinationOnSubscriptionLevel(
            String consumerSubaccountId, String providerSubaccountId,
            String applicationName, String destinationName);

    /**
     * Retrieve a destination with OAuth2SAMLBearerAssertion authentication type
     * that is defined for an application in the given provider subaccount and a
     * consumer subaccount subscribed to that application.
     * 
     * @param consumerSubaccountId
     *            - the id of the subaccount which is subscribed to the
     *            application for which a destination will be retrieved.
     * @param providerSubaccountId
     *            - the subaccount id of the application for which a destination
     *            will be retrieved.
     * @param applicationName
     *            - the name of the application for which a destination will be
     *            retrieved.
     * @param destinationName
     *            - the name of the destination to be retrieved.
     * 
     * @return the retrieved destination with OAuth2SAMLBearerAssertion
     *         authentication type.
     *
     * @throws UnauthorizedException
     *             - if token is null, or if the subaccount id is null or not
     *             valid.
     * @throws PermissionDeniedException
     *             - if the required user scope manageDestination is missing.
     * @throws DestinationNotFoundException
     *             - if the destination is not found.
     */
    SAMLBearerDestination retrieveSAMLBearerDestinationOnSubscriptionLevel(
            String consumerSubaccountId, String providerSubaccountId,
            String applicationName, String destinationName);

    /**
     * Retrieve a destination with Basic authentication type that is defined for
     * an application in the given provider subaccount and a consumer subaccount
     * subscribed to that application.
     * 
     * @param consumerSubaccountId
     *            - the id of the subaccount which is subscribed to the
     *            application for which a destination will be retrieved.
     * @param providerSubaccountId
     *            - the subaccount id of the application for which a destination
     *            will be retrieved.
     * @param applicationName
     *            - the name of the application for which a destination will be
     *            retrieved.
     * @param destinationName
     *            - the name of the destination to be retrieved.
     * 
     * @return the retrieved destination with Basic authentication type.
     *
     * @throws UnauthorizedException
     *             - if token is null, or if the subaccount id is null or not
     *             valid.
     * @throws PermissionDeniedException
     *             - if the required user scope manageDestination is missing.
     * @throws DestinationNotFoundException
     *             - if the destination is not found.
     */
    BasicDestination retrieveBasicDestinationOnSubscriptionLevel(String consumerSubaccountId,
            String providerSubaccountId, String applicationName, String destinationName);

    /**
     * Delete a destination that is defined for an application in the given
     * provider subaccount and a consumer subaccount subscribed to that
     * application.
     * 
     * @param consumerSubaccountId
     *            - the id of the subaccount which is subscribed to the
     *            application for which a destination will be deleted.
     * @param providerSubaccountId
     *            - the subaccount id of the application for which a destination
     *            will be deleted.
     * @param applicationName
     *            - the name of the application for which a destination will be
     *            deleted.
     * @param destinationName
     *            - the name of the destination to be deleted.
     *
     * @throws UnauthorizedException
     *             - if token is null, or if the subaccount id is null or not
     *             valid.
     * @throws PermissionDeniedException
     *             - if the required user scope manageDestination is missing.
     * @throws DestinationNotFoundException
     *             - if the destination is not found.
     */
    void deleteDestinationOnSubscriptionLevel(String consumerSubaccountId, String providerSubaccountId,
            String applicationName, String destinationName);

}