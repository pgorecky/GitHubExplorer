# GitHub Explorer API
The application allows to retrieve and process user's repositories from GitHub which are not forks and get detailed information about repository branches. 

GitHub Explorer API is an application developed using:
- Java 25
- Spring Boot 4.0
- Spring Cloud OpenFeign

### Architecture overview
The application acts as a proxy between the client and GitHub REST API (https://developer.github.com/v3).
It retrieves repositories for a given user, filters out forked repositories,
and enriches each repository with branch and commit information.

The application does not persist any data and communicates with GitHub API
in real time.

## Requirements
- Java 25
- Internet access

## How to install and run the application
1. Clone the repository:

    ```bash
    git clone https://github.com/pgorecky/GitHubExplorer.git
    ```

2. Go to the project directory:

    ```bash
    cd GitHubExplorer
    ```

3. Build a project using Maven:
    ```bash
    ./mvnw clean package
    ```

4. Run application using Maven:
    ```bash
    ./mvnw spring-boot:run
    ```

5. The application will be available at: `http://localhost:8080`

## How to use the application
After proper launching, the application is ready for use.
An API consumer can send a `GET` request to the endpoint `/api/repository/${username}` where username is the name of the user about whom we want to get information.

### Example usage in PowerShell
```bash
curl.exe http://localhost:8080/api/repository/pgorecky
```

### The response returned for the sample request
```json
[
  {
    "name": "GitHubExplorer",
    "owner": {
      "login": "pgorecky"
    },
    "branches": [
      {
        "name": "master",
        "commit": {
          "sha": "a68793ab57135e64ffb042c381cdba7ec467c608"
        }
      }
    ]
  },
  {
    "name": "WorldlyEats",
    "owner": {
      "login": "pgorecky"
    },
    "branches": [
      {
        "name": "main",
        "commit": {
          "sha": "c9bf74c823a5673c6bc3b8453889ba721def7ded"
        }
      }
    ]
  }
]
```

## Response scheme
### Response for existing user
```json
[
   {
      "name": "${repositoryName}",
      "owner": {
         "login": "${ownerLogin}"
      },
      "branches": [
         {
            "name": "${branchName}",
            "commit": {
               "sha": "${lastCommitSha}"
            }
         }
      ]
   }
]
```

### Response for non-existing user
```json
{
   "message": "User not found",
   "status": "404"
}
```
