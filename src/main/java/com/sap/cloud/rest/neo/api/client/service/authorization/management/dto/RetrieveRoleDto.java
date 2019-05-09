package com.sap.cloud.rest.neo.api.client.service.authorization.management.dto;

import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RetrieveRoleDto implements RoleDto {

    private final String name;
    private final String type;
    private final boolean applicationRole;
    private final boolean shared;

    private static final String NAME_JSON_PROPERTY = "name";
    private static final String SHARED_JSON_PROPERTY = "shared";
    private static final String APPLICATON_ROLE_JSON_PROPERTY = "applicatonRole";
    private static final String TYPE_JSON_PROPERTY = "type";

    @JsonCreator
    public RetrieveRoleDto(@JsonProperty(NAME_JSON_PROPERTY) String name, @JsonProperty(TYPE_JSON_PROPERTY) String type,
            @JsonProperty(APPLICATON_ROLE_JSON_PROPERTY) boolean applicationRole,
            @JsonProperty(SHARED_JSON_PROPERTY) boolean shared) {
        this.name = name;
        this.type = type;
        this.applicationRole = applicationRole;
        this.shared = shared;
    }

    @JsonProperty(NAME_JSON_PROPERTY)
    public String getName() {
        return name;
    }

    @JsonProperty(TYPE_JSON_PROPERTY)
    public String getType() {
        return type;
    }

    @JsonProperty(APPLICATON_ROLE_JSON_PROPERTY)
    public boolean isApplicationRole() {
        return applicationRole;
    }

    @JsonProperty(SHARED_JSON_PROPERTY)
    public boolean isShared() {
        return shared;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(RetrieveRoleDto.class.getName(), ToStringStyle.JSON_STYLE)
                .append(NAME_JSON_PROPERTY, name).append(TYPE_JSON_PROPERTY, type)
                .append(APPLICATON_ROLE_JSON_PROPERTY, applicationRole)
                .append(SHARED_JSON_PROPERTY, shared)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        RetrieveRoleDto that = (RetrieveRoleDto) o;
        return Objects.equals(name, that.name) && Objects.equals(type, that.type)
                && Objects.equals(applicationRole, that.applicationRole) && Objects.equals(shared, that.shared);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, applicationRole, shared);
    }

}
