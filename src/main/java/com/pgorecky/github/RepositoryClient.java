package com.pgorecky.github;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;

@FeignClient(value = "githubClient", url = "${github.api.url}")
interface RepositoryClient {

    @GetMapping( "/users/{username}/repos")
    List<Repository> getAllRepositories(@PathVariable String username);

    @GetMapping("/repos/{username}/{repo}/branches")
    Set<Branch> getAllRepoBranches(@PathVariable String username, @PathVariable String repo);
}
