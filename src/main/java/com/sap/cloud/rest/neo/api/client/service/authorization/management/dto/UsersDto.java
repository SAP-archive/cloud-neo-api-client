package com.sap.cloud.rest.neo.api.client.service.authorization.management.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UsersDto {

    private static final String AUTHORIZATION_MANAGEMENT_JSON_PROPERTY = "users";

    private final List<UserDto> usersList;

    @JsonCreator
    public UsersDto(@JsonProperty(AUTHORIZATION_MANAGEMENT_JSON_PROPERTY) final List<UserDto> usersList) {
        this.usersList = usersList;
    }

    @JsonProperty(AUTHORIZATION_MANAGEMENT_JSON_PROPERTY)
    public List<UserDto> getUsersList() {
        return usersList == null ? null : new ArrayList<>(usersList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usersList);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        UsersDto other = (UsersDto) obj;
        return Objects.equals(usersList, other.usersList);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(RolesDto.class.getName(), ToStringStyle.JSON_STYLE)
                .append(AUTHORIZATION_MANAGEMENT_JSON_PROPERTY, usersList)
                .build();
    }
}
