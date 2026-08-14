package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import java.util.List;

public class SearchGitPage extends BasePage {

    public SearchGitPage(WebDriver driver) {
        setDriver(driver);
        PageFactory.initElements(
                new AjaxElementLocatorFactory(driver, 10),
                this);
    }

    By repoTitle =
            By.xpath("//div[contains(@class,'search-title')]");
    By profileImg =
            By.xpath("//img[@data-testid='github-avatar']");
    @FindBy(id = "search-results-count") //h2
    WebElement countResults;
    @FindBy(xpath = "//a[@aria-label='Next Page']")
    WebElement btnNext;

    public void clickPageNext() {
        clickWait(btnNext);
    }

    public int getTotalSearchResults() {
        String resString = countResults.getText();
        String numberResults = resString.split(" result")[0];

        if (!numberResults.contains("M") && !numberResults.contains("k")) {
            return Integer.parseInt(numberResults);
        } else
            //error: too many repositories
            return -1;
    }

    public boolean isRepoNameInSearchResultsOnThePage(String repoName) {
        List<WebElement> searchTitles = driver.findElements(repoTitle);
        if (searchTitles.isEmpty())
            return false;

        for (WebElement title : searchTitles) {
            if (title.getText().toUpperCase().contains(repoName.toUpperCase()))
                return true;
        }
        return false;
    }

    public int getNumberOfProfilePicturesOnThePage() {
        return driver.findElements(profileImg).size();
    }
}
