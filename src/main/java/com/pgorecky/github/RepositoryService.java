package com.pgorecky.github;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
class RepositoryService {

    private final RepositoryClient repositoryClient;

    public List<Repository> getAllNonForkRepositoriesWithBranchesByUsername(String username) {
        log.info("Fetching non-fork repositories with branches for user: {}", username);

        List<Repository> repositories = repositoryClient.getAllRepositories(username)
                .stream()
                .filter(repository -> !repository.isFork())
                .toList();

        repositories.forEach(repository ->
                repository.setBranches(
                        repositoryClient.getAllRepoBranches(username, repository.getName())
                )
        );

        return repositories;
    }
}

