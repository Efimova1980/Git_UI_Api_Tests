package api_rest;

import io.restassured.response.Response;
import utils.BaseApi;

import static io.restassured.RestAssured.given;

public class GitHubApiClient implements BaseApi {

    public Response searchRepositories(String repoName) {

        return given().baseUri(BASE_URL)
                .queryParam("q", "repo:" + repoName)
                .when()
                .get(SEARCH_REPOSITORIES)
                .then()
                .statusCode(200)
                .extract()
                .response();
    }
}
