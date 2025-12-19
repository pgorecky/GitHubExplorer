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

    public List<Repository> getAllRepositoriesWithBranchesByUsername(String username) {
        log.info("Fetching non-fork repositories with branches for user: {}", username);

        return repositoryClient.getAllRepositories(username)
                .stream()
                .filter(repository -> !repository.isFork())
                .map(repository -> setRepositoryBranch(repository, username))
                .toList();
    }

    private Repository setRepositoryBranch(Repository repository, String username) {
        repository.setBranches(
                repositoryClient.getAllRepoBranches(username, repository.getName())
        );
        return repository;
    }
}

