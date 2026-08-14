package ui_tests;

import manager.AppManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.HomeGitPage;
import pages.SearchGitPage;
import utils.TestData;

public class FindRepoTests extends AppManager {

    String repoName;
    SoftAssert softAssert = new SoftAssert();
    HomeGitPage homeGitPage;
    SearchGitPage searchGitPage;
    final static int MAX_PAGE_FOR_SEARCH = 5;

    @BeforeMethod(alwaysRun = true)
    public void searchRepo() {
        repoName = System.getProperty("repoName",
                TestData.DEFAULT_REPO_NAME);
        homeGitPage = new HomeGitPage(getDriver());
        homeGitPage.openSearchForm();
        homeGitPage.typeTextToSearchForm(repoName);
        homeGitPage.pause(2);
    }

    @Test(enabled = true)
    public void FindRepoAndCompareResults_Test() {
        searchGitPage = new SearchGitPage(getDriver());
        int totalNumberOfRepo = searchGitPage.getTotalSearchResults();

        if (totalNumberOfRepo == 0) {
            Assert.fail("Search results is 0");
        } else if (totalNumberOfRepo == -1) {
            throw new org.testng.SkipException("Test skipped: too many results");
        }

        int numberOfProfilePictures = 0;
        boolean repoNameInSearchResults = false;
        int lastPage = (totalNumberOfRepo + 9) / 10;

        for (int i = 1; i <= lastPage && i <= MAX_PAGE_FOR_SEARCH; i++) {
            numberOfProfilePictures += searchGitPage.getNumberOfProfilePicturesOnThePage();
            repoNameInSearchResults |= searchGitPage.isRepoNameInSearchResultsOnThePage(repoName);
            if (i < lastPage && i < MAX_PAGE_FOR_SEARCH)
                searchGitPage.clickPageNext();
            //pause is to safely paginate
            searchGitPage.pause(2);
        }

        if (lastPage <= MAX_PAGE_FOR_SEARCH)
            softAssert.assertEquals(numberOfProfilePictures, totalNumberOfRepo,
                    "validate number of profile pictures");
        else
            System.out.println("Profile pictures count check skipped: too many results to safely paginate through");

        softAssert.assertTrue(repoNameInSearchResults,
                "validate if repo is in search results");
        softAssert.assertAll();
    }
}
