package api_tests;

import dto.Item;
import dto.Items;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.BaseApi;

import java.io.IOException;

public class FindRepoAPITests implements BaseApi {

    String repoName;
    SoftAssert softAssert = new SoftAssert();

    @Test
    public void findRepoPositiveTest(){
        repoName = System.getProperty("repoName", "Efimova1980/QA_50_32");
        Request request = new Request.Builder()
                .url(BASE_URL + SEARCH_REPO + repoName)
                .get()
                .build();

        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()){
            Items items = GSON.fromJson(response.body().string(), Items.class);
            for (Item item: items.getItems()){
                System.out.println(item.getFull_name() + ": is private="+ item.isPrivate());
            }
            softAssert.assertEquals(items.getTotal_count(), 1);
            softAssert.assertFalse(items.getItems().get(0).isPrivate());
            softAssert.assertAll();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
