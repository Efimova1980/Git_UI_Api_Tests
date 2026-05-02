package ui_tests;

import manager.AppManager;
import org.checkerframework.checker.units.qual.A;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.HomeGitPage;
import pages.SearchGitPage;

public class FindRepoTests extends AppManager {

    String repoName ;
    SoftAssert softAssert = new SoftAssert();

    @BeforeMethod(alwaysRun = true)
    public void searchRepo(){
        repoName = System.getProperty("repoName", "Efimova1980/QA_50_32");
        HomeGitPage homeGitPage = new HomeGitPage(getDriver());
        homeGitPage.searchFormClick();
        homeGitPage.typeInputSearch(repoName);
        homeGitPage.pause(3); //search page is refreshing, element are getting older
    }

    @Test
    public void FindRepoAndCheckNumberAvatars(){
        SearchGitPage searchGitPage = new SearchGitPage(getDriver());
        searchGitPage.clickMenuRepositories();
        searchGitPage.pause(3);
        softAssert.assertTrue(searchGitPage.isRepoInTheSearchResults(repoName), "validate repo is on the page");
        softAssert.assertTrue(searchGitPage.isTotalResultsEqualToNumberProfilePictures(), "validate number of avatars");
        softAssert.assertAll();
    }


    // tests with clicking pages
    @Test(enabled = false)
    public void FindRepoPositiveTests(){
        SearchGitPage searchGitPage = new SearchGitPage(getDriver());
        searchGitPage.clickMenuRepositories();
        searchGitPage.pause(3);
        Assert.assertTrue(searchGitPage.isRepoInTheSearchResults(repoName));
    }

    @Test(enabled = false)
    public void FindRepoCompareResultsPositiveTests(){
        SearchGitPage searchGitPage = new SearchGitPage(getDriver());
        searchGitPage.clickMenuRepositories();
        searchGitPage.pause(3);
        Assert.assertTrue(searchGitPage.isTotalResultsEqualToNumberProfilePictures());
    }

}
