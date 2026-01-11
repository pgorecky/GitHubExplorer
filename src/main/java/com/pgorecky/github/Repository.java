package com.pgorecky.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Repository {

    private String name;

    private Owner owner;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean fork;

    private Set<Branch> branches = new HashSet<>();
}
