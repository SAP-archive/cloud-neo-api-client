package com.sap.cloud.rest.neo.api.client.service.authorization.management.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RolesDto<T extends RoleDto> {

    private static final String AUTHORIZATION_MANAGEMENT_JSON_PROPERTY = "roles";

    private final List<T> rolesList;

    @JsonCreator
    public RolesDto(@JsonProperty(AUTHORIZATION_MANAGEMENT_JSON_PROPERTY) final List<T> rolesList) {
        this.rolesList = rolesList;
    }

    @JsonProperty(AUTHORIZATION_MANAGEMENT_JSON_PROPERTY)
    public List<T> getRolesList() {
        return rolesList == null ? null : new ArrayList<>(rolesList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rolesList);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        RolesDto other = (RolesDto) obj;
        return Objects.equals(rolesList, other.rolesList);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(RolesDto.class.getName(), ToStringStyle.JSON_STYLE)
                .append(AUTHORIZATION_MANAGEMENT_JSON_PROPERTY, rolesList)
                .build();
    }

}
