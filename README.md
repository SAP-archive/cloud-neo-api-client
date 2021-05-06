![](https://img.shields.io/badge/STATUS-NOT%20CURRENTLY%20MAINTAINED-red.svg?longCache=true&style=flat)

# Important Notice
This public repository is read-only and no longer maintained.

# SAP Cloud Platform Neo API Client

A Java client for SAP Cloud Platform Neo Platform APIs.
List of currently supported platform APIs:
- Authorization Management
- Configuration Service

## Prerequisities and Dependencies

- Java 8
- [SAP Rest API Client](https://github.com/SAP/cloud-rest-api-client)
- [Apache HTTP Client](https://hc.apache.org/httpcomponents-client-ga/index.html)
- [Jackson Faster XML](https://github.com/FasterXML/jackson)
- [Apache commons lang](https://commons.apache.org/proper/commons-lang/)  
- [Apache commons io](https://commons.apache.org/proper/commons-io/)

## Installation

Build the library using the following maven command.

```
mvn clean install
```

## Testing

### Unit tests

The unit tests of the library can be executed with the following Maven command

```
mvn clean install -Punit-tests
```

### Integration tests

#### Prerequisites

* __SAP CP Subaccount__ - the subaccount that would be integrated
* __Application__ - an application, deployed in the subaccount, in which destinations will be managed
* __SAP CP Provider Subaccount__ - the subaccount that Provided Application is deployed in
* __Provided Application__ - an application that SAP CP Subaccount is subscribed to
* __SAP Cloud Platform Neo Platform API OAuth Client__ - SAP Cloud Platform API OAuth client with scopes for at least all currently supported APIs

#### Configuration Properties

|Property                   |Description                                                        |
|:-------------------------:|:-----------------------------------------------------------------:|
|regionAlias                | the region alias of the landscape on which APIs are being invoked.|
|subaccount                 | the platform ID of __SAP CP Subaccount__                          |
|applicationName            | the name of __Application__                                       |
|providerSubaccount         | the platform ID of __SAP CP Provider Subaccount__                 |
|providerApplication        | the name of __Provided Application__                              |
|platformClientId           | the client ID of __Platform API Client__                          |
|platformClientSecret       | the client secret of __Platform API Client__                      |

#### Trigger Integration Tests

```
mvn clean install -Pintegration-tests \
-DregionAlias=<subaccountRegionAlias> \
-Dsubaccount=<subaccountId> \
-DplatformClientId=<platformClientId> \
-DplatformClientSecret=<platformClientSecret> \
-DapplicationName=<applicationName> \
-DproviderSubaccount=<providerSubaccountId> \
-DproviderApplication=<providerApplicationName>
```

# Usage

Add the following dependency to your maven __pom.xml__ file.

```java
<dependency>
    <groupId>com.sap.cloud.rest</groupId>
    <artifactId>neo-api-client</artifactId>
    <version>${latest.version}</version>
</dependency>
```

## Creating Neo API Client and Obtaining Service Clients


```java
   OAuthAuthentication authentication = PlatformClientsOAuthAuthenticationBuilder.getBuilder()
                .subaccount("<subaccount>")
                .regionAlias("<region_alias>")
                .clientID("<client_id>")
                .clientSecret("<client_secret>".toCharArray())
                .build();
                
  // Create Neo API Client Factory
  NeoClientFactory neoClientFactory = new NeoClientFactory(authentication);
  
  // Get Authorization Management Service Client
  AuthorizationManagementService authorizationService = neoClientFactory.getAuthorizationManagementService("neo-region","subaccount");
  
  // Get Configiration Service Client
  DestinationConfigurationService destinationService = neoClientFactory.getDestinationConfigurationService("neo-region");
   
```

or you could construct the clients on your own by passing a __RestApiClientConfig__ to the constructor. Refer to the rest-api-client library for more info.

```java
OAuthServerConfig oAuthServerConfig = OAuthServerConfigBuilder.getBuilder()
    .oAuthServerHost("<oauth_server_host>")
    .oAuthServerApiPath("<oauth_server_port>")
    .clientID("<client_id>")
    .clientSecret("<client_secret>".toCharArray())
    .build();

RestApiClientConfig restApiClientConfig = RestApiClientConfigBuilder.getBuilder()
    .host("<host>")
    .oAuthAuthentication(oAuthServerConfig)
    .build();

// Create Authorization Management Service Client
AuthorizationManagementService authorizationService = new AuthorizationManagementRestApiClient(restApiClientConfig);

// Create Configiration Service Client
DestinationConfigurationService destinationConfigurationService = new DestinationConfigurationRestApiClient(restApiClientConfig);
```

## Destination Configuration Service

* Upsert a Destination on Tenant level (destinationConfiguration parameter could be instance of [DestinationConfiguration](#creating-a-destination-configuration) or any of its subclasses, for example [BasicDestination](#creating-a-basic-destination), [SAMLBearerDestination](#creating-a-saml-bearer-destination))

```java
    destinationConfigurationService.upsertDestinationOnTenantLevel(<subaccountId>, <destinationConfiguration>)
```
    
* Retrieve Destination with OAuth2SAMLBearerAssertion Authentication Type on Tenant Level

```java
    OSAMLBearerDestination destination = destinationConfigurationService
    .retrieveSAMLBearerDestinationOnTenantLevel(<subaccountId>, <destinationName>)
```

* Retrieve Destination with Basic Authentication Type on Tenant Level

```java
    BasicDestination destination = destinationConfigurationService
    .retrieveBasicDestinationOnTenantLevel(<subaccountId>, <destinationName>)
```
 
* Retrieve Destination on Tenant Level (supported Authentication types are NoAuthentication, BasicAuthentication, ClientCertificateAuthentication or OAuth2SAMLBearerAssertion) 

```java
    DestinationConfiguration destination = destinationConfigurationService
    .retrieveDestinationOnTenantLevel(<subaccountId>, <destinationName>)
```
 
* Delete Destination on Tenant Level

```java
    destinationConfigurationService.deleteDestinationOnTenantLevel(<subaccountId>, <destinationName>)
```

* Upsert a Destination on Application level (destinationConfiguration parameter could be instance of [DestinationConfiguration](#creating-a-destination-configuration) or any of its subclasses, for example [BasicDestination](#creating-a-basic-destination), [SAMLBearerDestination](#creating-a-saml-bearer-destination))

```java
    destinationConfigurationService.upsertDestinationOnApplicationLevel(<subaccountId>, <applicationName>, <destinationConfiguration>)
```
 
* Retrieve Destination with OAuth2SAMLBearerAssertion Authentication Type on Application Level

```java
    OAuth2SAMLBearerAssertionDestination destination = destinationConfigurationService
    .retrieveSAMLBearerDestinationOnApplicationLevel(<subaccountId>, <applicationName>, <destinationName>)
```

* Retrieve Destination with Basic Authentication Type on Application Level

```java
    BasicDestination destination = destinationConfigurationService
    .retrieveBasicDestinationOnApplicationLevel(<subaccountId>, <applicationName>, <destinationName>)
```

* Retrieve Destination on Application Level (supported Authentication types are NoAuthentication, BasicAuthentication, ClientCertificateAuthentication or OAuth2SAMLBearerAssertion) 

```java
    DestinationConfiguration destination = destinationConfigurationService
    .retrieveDestinationOnApplicationLevel(<subaccountId>, <applicationName>, <destinationName>)
```
 
* Delete Destination on Application Level

```java
    destinationConfigurationService.deleteDestinationOnApplicationLevel(<subaccountId>, <applicationName>, <destinationName>)
```

* Upsert a Destination on Subscription Level (destinationConfiguration parameter could be instance of [DestinationConfiguration](#creating-a-destination-configuration) or any of its subclasses, for example [BasicDestination](#creating-a-basic-destination), [SAMLBearerDestination](#creating-a-saml-bearer-destination))

```java
    destinationConfigurationService.upsertDestinationOnSubscriptionLevel(<consumerSubaccountId>, <providerSubaccountId>, <applicationName>, <destinationConfiguration>)
```

* Retrieve Destination with OAuth2SAMLBearerAssertion Authentication Type on Subscription Level

```java
    SAMLBearerDestination destination = destinationConfigurationService
    .retrieveSAMLBearerDestinationOnSubscriptionLevel<consumerSubaccountId>, <providerSubaccountId>, <applicationName>, <destinationName>)
```

* Retrieve Destination with Basic Authentication Type on Subscription Level

```java
    BasicDestination destination = destinationConfigurationService
    .retrieveBasicDestinationOnSubscriptionLevel(<consumerSubaccountId>, <providerSubaccountId>, <applicationName>, <destinationName>)
```
 
* Retrieve Destination on Subscription Level (supported Authentication types are NoAuthentication, BasicAuthentication, ClientCertificateAuthentication or OAuth2SAMLBearerAssertion)

```java
    DestinationConfiguration destination = destinationConfigurationService
    .retrieveDestinationOnSubscriptionLevel(<consumerSubaccountId>, <providerSubaccountId>, <applicationName>, <destinationName>)
```
 
* Delete Destination on Subscription Level

```java
    destinationConfigurationService.deleteDestinationOnSubscriptionLevel(<consumerSubaccountId>, <providerSubaccountId>, <applicationName>)
```

### Creating a Destination Configuration

```java
AuthenticationType authenticationType; // initialize
ProxyType proxyType; // initialize
DestinationType destinationType; // initialize
DestinationConfiguration destinationConfiguration = new DestinationConfiguration("<destinationName>", "<url>",
        authenticationType, proxyType, destinationType);
```
    
You can also provide custom properties in a __Map__

```java
Map<String, String> additionalProperties = new HashMap<>();
additionalProperties.put("property", "value");
DestinationConfiguration destinationConfiguration = new DestinationConfiguration("<destinationName>", "<url>",
        authenticationType, proxyType, destinationType, additionalProperties);
```
 
### Creating a Basic Destination

```java
ProxyType proxyType; // initialize
BasicDestination basicDestination new BasicDestination("<destinationName>",
            "<url>", proxyType, "<user>", "<password>".toCharArray);
```

You can also provide custom properties in a __Map__

```java
Map<String, String> additionalProperties = new HashMap<>();
additionalProperties.put("property", "value");
BasicDestination basicDestination new BasicDestination("<destinationName>",
            "<url>", proxyType, "<user>", "<password>".toCharArray, additionalProperties);
```

### Creating a SAML Bearer Destination

```java
ProxyType proxyType; // initialize
SAMLBearerDestination oAuthDestination = new SAMLBearerDestination("<destinationName>", "<url>", 
proxyType, "<audience>", "<client_key>".toCharArray, "<tokenServiceUrl>", "<tokenServiceUser>", 
"<tokenServicePassword>".toCharArray());
```
    
You can also provide custom properties in a __Map__

```java
Map<String, String> additionalProperties = new HashMap<>();
additionalProperties.put("property", "value");
SAMLBearerDestination oAuthDestination = new SAMLBearerDestination("<destinationName>", "<url>", 
proxyType, "<audience>", "<client_key>".toCharArray, "<tokenServiceUrl>", "<tokenServiceUser>", 
"<tokenServicePassword>".toCharArray(), additionalProperties);
```

## Authorization Management Service


* Create a new role with the specified name, for the specified account and application
 
```java
    authorizationManagementService.createRole("accountName", "appName", "providerAccount", "role");
``` 

* Create a list of new roles for the specified account and application

```java
    List<String> roles = Arrays.asList("role1", "role2");
    authorizationManagementService.createRoles("accountName", "appName", "providerAccount", roles);
``` 

* Retrieve a list of all roles for the specified account and application

```java
    List<Role> roles = authorizationManagementService.retrieveRoles("accountName", "appName", "providerAccount");
```

* Deletes a role for the specified account and application

```java
    authorizationManagementService.deleteRole("accountName", "appName", "providerAccount", "role");
```
 
* Deletes a list of roles for the specified account and application

```java
    List<String> roles = Arrays.asList("role1", "role2");
    authorizationManagementService.deleteRoles("accountName", "appName", "providerAccount", roles);
```

* Assigns the specified user a role for the specified account and application

```java
    authorizationManagementService.assignUserToRole("accountName", "appName", "providerAccount", "role", "username");
``` 
 
* Assigns a list of users a role for the specified account and application

```java
    List<String> usernames = Arrays.asList("user1", "user2");
    authorizationManagementService.assignUsersToRole("accountName", "appName", "providerAccount", "role", usernames);
``` 
 
 * Retrieve all users which have the specified role assinged for the specified account and application

```java
    List<User> users = authorizationManagementService.retrieveUsersAssignedToRole("accountName", "appName", "providerAccount", "role");
```

* Unassigns the specified user from a role for the specified account and application

```java
    authorizationManagementService.unassignUserFromRole("accountName", "appName", "providerAccount", "role", "username");
```
 
* Unassigns a list of users from a role for the specified account and application

```java
    List<String> usernames = Arrays.asList("user1", "user2");
    authorizationManagementService.unassignUsersFromRole("accountName", "appName", "providerAccount", "role", usernames);
```

* Unassigns all roles assigned to the specified user

```java
    authorizationManagementService.unassignAllRolesFromUser("accountName", "userName");
```

## Utility Classes

### ConfigApiHostProvider 

Provides the host for the Neo Configuration API by specified region alias.

### AuthorizationManagementHostProvider 

Provides the host for the Authorization Management API by specified region alias and subaccount.

## Thrown Exceptions

### General

* __ConnectionException__ if an IOException occurs while executing an HTTP request.
* __ResponseException__ if an error HTTP status code is received.
* __UnauthorizedException__ if the user is not authorized to do the operation.

### Configuration Service

* __DestinationNotFoundException__ if the destination to be retrieved or deleted is not found
* __PermissionDeniedException__ if required authorization scope is missing

# Contribution

We welcome any exchange and collaboration with individuals and organizations interested in the use, support and extension of this library.

# License
Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved. This file is licensed under the Apache Software License, v. 2 except as noted otherwise in the [LICENSE file](/LICENSE.txt)
