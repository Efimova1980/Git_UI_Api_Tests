package utils;

import com.google.gson.Gson;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;

//https://docs.github.com/en/rest/search/search?apiVersion=2026-03-10#search-repositories
//https://docs.github.com/en/search-github/searching-on-github/searching-for-repositories

public interface BaseApi {
    //<----------------------end points----------------->
    String BASE_URL = "https://api.github.com";
    String SEARCH_REPO = "/search/repositories?q=repo:";

    Gson GSON = new Gson();
    MediaType JSON = MediaType.get("application/json");
    OkHttpClient OK_HTTP_CLIENT = new OkHttpClient();

}
