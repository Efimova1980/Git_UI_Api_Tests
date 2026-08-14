package api_tests;

import api_rest.GitHubApiClient;
import dto.Items;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.BaseApi;
import utils.SchemaValidator;
import utils.TestData;

import java.io.IOException;

public class FindRepoAPITests implements BaseApi {

    String repoName;
    SoftAssert softAssert = new SoftAssert();
    GitHubApiClient gitHubApiClient = new GitHubApiClient();
    Response response;

    @Test
    public void findRepoPositiveTest() throws IOException {

        repoName = System.getProperty("repoName",
                TestData.DEFAULT_REPO_NAME);

        response = gitHubApiClient.searchRepositories(repoName);

        Assert.assertNotNull(response.getBody());

        Items items = response.as(Items.class);
        String responseBody = response.getBody().asString();

        softAssert.assertTrue(SchemaValidator.validateJsonSchema(responseBody,
                        "src/test/resources/schema.json").isEmpty(),
                "validate json schema");

        softAssert.assertEquals(items.getTotal_count(), 1,
                "validate that count of results is 1");

        softAssert.assertFalse(items.getItems().get(0).isPrivate(),
                "validate that the repo is public");

        softAssert.assertAll();
    }
}