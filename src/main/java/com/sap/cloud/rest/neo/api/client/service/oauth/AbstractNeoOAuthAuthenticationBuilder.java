package com.sap.cloud.rest.neo.api.client.service.oauth;

import com.sap.cloud.rest.api.client.auth.oauth.OAuthAuthentication;

/**
 * Base Class for Neo Authentication Builder implementations. 
 * @param <Builder> Authentication builder implementation.
 * @param <NeoClientsAuthentication> Authentication configuration implementation.
 */
public abstract class AbstractNeoOAuthAuthenticationBuilder<Builder extends AbstractNeoOAuthAuthenticationBuilder<Builder, NeoClientsAuthentication>, NeoClientsAuthentication extends OAuthAuthentication> {

    protected String regionAlias;
    protected String clientID;
    protected char[] clientSecret;

    /**
     * Attaches a region alias to the builder.
     * @param regionAlias Region alias. 
     * @return Builder instance.
     */
    public Builder regionAlias(final String regionAlias) {
        this.regionAlias = regionAlias;
        return self();
    }

    /**
     * Attaches a client ID to the builder.
     * @param clientID Client ID.
     * @return Builder instance.
     */
    public Builder clientID(final String clientID) {
        this.clientID = clientID;
        return self();
    }

    /**
     * Attaches a client secret to the builder.
     * @param clientSecret Client secret.
     * @return Builder instance.
     */
    public Builder clientSecret(final char[] clientSecret) {
        this.clientSecret = clientSecret;
        return self();
    }

    /**
     * Override to return an instance of {@link OAuthAuthentication} with the
     * attached to the builder region alias, client id, client secret and any
     * subclass properties.
     * @return Returns authentication configuration.
     */
    public abstract NeoClientsAuthentication build();

    /**
     * Override to return the instance of the subclass {@link Builder}.
     * @return Builder instance.
     */
    protected abstract Builder self();

}
