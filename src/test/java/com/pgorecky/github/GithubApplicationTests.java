package com.pgorecky.github;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class GithubApplicationTests {
    private final WireMockServer wireMockServer = new WireMockServer(8083);

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void startWireMockServer() {
        wireMockServer.start();
        configureFor("localhost", 8083);
    }

    @AfterEach
    public void stopWireMockServer() {
            wireMockServer.stop();
    }

    @Test
    void shouldReturnNonForksRepositoriesWithBranchesWhenUserExists() throws Exception {
        // given: mock repositories response
        stubFor(
                WireMock.get(urlPathEqualTo("/users/testUser/repos"))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody("""
                                        [
                                            {
                                                "name": "mock-repo1",
                                                "owner": {
                                                            "login": "testUser"
                                                },
                                                "fork": false
                                            },
                                            {
                                                "name": "mock-forked-repo2",
                                                "owner": {
                                                            "login": "testUser"
                                                },
                                                "fork": true
                                            }
                                        ]
                                        """))
        );

        // given: mock branches response
        stubFor(
                WireMock.get(urlPathEqualTo("/repos/testUser/mock-repo1/branches"))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody("""
                                        [
                                            {
                                                "name": "master",
                                                "commit": {
                                                    "sha": "438acbc2358f4fd34acbd8e5f0537be4a6555124"
                                                }
                                            },
                                            {
                                                "name": "develop",
                                                "commit": {
                                                    "sha": "438acbc2358f4fd34acbd8e5f4537tr4a6555454"
                                                }
                                            }
                                        ]
                                        """))
        );

        // then: test controller response
        mockMvc.perform(get("/api/repository/testUser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("mock-repo1"))
                .andExpect(jsonPath("$[0].branches[0].name").value("master"))
                .andExpect(jsonPath("$[0].branches[1].name").value("develop"));
    }

    @Test
    void shouldReturnNotFoundWhenUserNotExists() throws Exception {
        // given: mock user not found response
        stubFor(
                WireMock.get(urlPathEqualTo("/users/testUser/repos"))
                        .willReturn(aResponse()
                                .withHeader("Content-Type", "application/json")
                                .withStatus(404)
                                .withBody("""
                                        {
                                            "message": "Not Found",
                                            "documentation_url": "https://docs.github.com/rest/repos/repos#list-repositories-for-a-user",
                                            "status": "404"
                                        }
                                        """))

        );

        // then: test controller response
        mockMvc.perform(get("/api/repository/testUser"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found"))
                .andExpect(jsonPath("$.status").value("404"));
    }


}
