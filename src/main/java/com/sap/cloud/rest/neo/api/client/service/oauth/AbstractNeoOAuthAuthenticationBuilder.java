package com.sap.cloud.rest.neo.api.client.service.oauth;

import com.sap.cloud.rest.api.client.auth.oauth.OAuthAuthentication;

public abstract class AbstractNeoOAuthAuthenticationBuilder<Builder extends AbstractNeoOAuthAuthenticationBuilder<Builder, NeoClientsAuthentication>, NeoClientsAuthentication extends OAuthAuthentication> {

    protected String regionAlias;
    protected String clientID;
    protected char[] clientSecret;

    /**
     * Attaches a region alias to the builder.
     */
    public Builder regionAlias(final String regionAlias) {
        this.regionAlias = regionAlias;
        return self();
    }

    /**
     * Attaches a client ID to the builder.
     */
    public Builder clientID(final String clientID) {
        this.clientID = clientID;
        return self();
    }

    /**
     * Attaches a client secret to the builder.
     */
    public Builder clientSecret(final char[] clientSecret) {
        this.clientSecret = clientSecret;
        return self();
    }

    /**
     * Override to return an instance of {@link OauthAuthentication} with the
     * attached to the builder region alias, client id, client secret and any
     * subclass properties.
     */
    public abstract NeoClientsAuthentication build();

    /**
     * Override to return the instance of the subclass {@link Builder}.
     */
    protected abstract Builder self();

}
