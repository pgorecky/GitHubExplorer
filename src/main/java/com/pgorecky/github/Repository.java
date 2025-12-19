package com.pgorecky.github;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Set;

public record Repository(
        String name,
        Owner owner,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        boolean fork,
        Set<Branch> branches
) {
    public Repository {
        branches = new HashSet<>();
    }
}
