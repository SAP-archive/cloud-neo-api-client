package com.sap.cloud.rest.neo.api.client.service.oauth.platform;

import com.sap.cloud.rest.neo.api.client.service.oauth.AbstractNeoOAuthAuthenticationBuilder;

/**
 * A builder for {@link PlatformClientsOAuthAuthentication}.
 */
public class PlatformClientsOAuthAuthenticationBuilder extends
        AbstractNeoOAuthAuthenticationBuilder<PlatformClientsOAuthAuthenticationBuilder, PlatformClientsOAuthAuthentication> {

    private String subaccount;

    /**
     * Attaches a subaccount to the builder.
     * @param subaccount Subaccount.
     * @return Builder instance.
     */
    public PlatformClientsOAuthAuthenticationBuilder subaccount(final String subaccount) {
        this.subaccount = subaccount;
        return self();
    }

    /**
     * Builds a {@link PlatformClientsOAuthAuthentication} with the provided
     * region alias, subaccount, client ID and client secret.
     * @return Platform client OAuth authentication configuration instance.
     */
    @Override
    public PlatformClientsOAuthAuthentication build() {
        return new PlatformClientsOAuthAuthentication(regionAlias, subaccount, clientID, clientSecret);
    }

    /**
     * @return Returns an instance of {@link PlatformClientsOAuthAuthenticationBuilder}.
     */
    public static PlatformClientsOAuthAuthenticationBuilder getBuilder() {
        return new PlatformClientsOAuthAuthenticationBuilder();
    }

    @Override
    protected PlatformClientsOAuthAuthenticationBuilder self() {
        return this;
    }
}