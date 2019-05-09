package com.sap.cloud.rest.neo.api.client.service.authorization.management.model;

import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Role {

    private final String name;
    private final String type;
    private final boolean applicationRole;
    private final boolean shared;

    public Role(String name, String type, boolean applicationRole, boolean shared) {
        this.name = name;
        this.type = type;
        this.applicationRole = applicationRole;
        this.shared = shared;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean isApplicationRole() {
        return applicationRole;
    }

    public boolean isShared() {
        return shared;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(Role.class.getName(), ToStringStyle.JSON_STYLE)
                .append("name", name)
                .append("type", type)
                .append("applicatonRole", applicationRole)
                .append("shared", shared)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Role that = (Role) o;
        return Objects.equals(name, that.name) && Objects.equals(type, that.type)
                && Objects.equals(applicationRole, that.applicationRole) && Objects.equals(shared, that.shared);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, applicationRole, shared);
    }

}
