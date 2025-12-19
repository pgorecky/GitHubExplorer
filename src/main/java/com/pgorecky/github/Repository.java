package com.pgorecky.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Set;

@Data
public class Repository {
    String name;
    Owner owner;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean fork;
    Set<Branch> branches;

}
