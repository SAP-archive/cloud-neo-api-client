package com.sap.cloud.rest.neo.api.client.service.authorization.management.dto;

import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDto {

    private static final String NAME_JSON_PROPERTY = "name";

    private final String name;

    @JsonCreator
    public UserDto(@JsonProperty(NAME_JSON_PROPERTY) final String name) {
        this.name = name;
    }

    @JsonProperty(NAME_JSON_PROPERTY)
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(UserDto.class.getName(), ToStringStyle.JSON_STYLE)
                .append(NAME_JSON_PROPERTY, name)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDto that = (UserDto) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
