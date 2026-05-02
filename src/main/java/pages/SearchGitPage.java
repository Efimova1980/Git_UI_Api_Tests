package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import java.util.List;

public class SearchGitPage extends BasePage{
    public SearchGitPage(WebDriver driver) {
        setDriver(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    By btnNext  = By.xpath("//a[@aria-label='Next Page']");
    By searchTitle = By.xpath("//div[@class='search-title Header-module__title__QUX7e']");
    By searchAvatar = By.xpath("//img[@data-testid='github-avatar']");

    @FindBy(xpath = "//a[@data-testid='nav-item-repositories']")
    WebElement menuRepo;
    @FindBy(id="search-results-count") //h2
    WebElement countResults;

    public void clickMenuRepositories(){
        clickWait(menuRepo);
    }

    public int getTotalNumberRepo(){
        String resString = countResults.getText();
        String numberResults = resString.split(" result")[0];
        String number;
        int mult ;

        if (numberResults.contains("M")){
            mult = 1_000_000;
            number = numberResults.substring(0, numberResults.length()-1);
        } else if (numberResults.contains("k")) {
            mult = 1_000;
            number = numberResults.substring(0, numberResults.length()-1);
        }else {
            mult = 1;
            number = numberResults;
        }
        return (int)(Double.parseDouble(number)*mult);
    }


    //simple code without clicking pages for one search page
    public boolean isTotalResultsEqualToNumberProfilePictures_onePage(){
        return (getTotalNumberRepo() == driver.findElements(searchAvatar).size());
    }
    public boolean isRepoInTheSearchResults_onePage(String repoName){
        List<WebElement> searchTitles = driver.findElements(searchTitle);
        if (searchTitles.isEmpty())
            return false;

        for (WebElement title: searchTitles){
            if (isRopositoriesAreEquals(title.getText(), repoName))
                return true;
        }
        return false;
    }

    public boolean isRopositoriesAreEquals(String searchTitle, String repoName){
        String repo1 = searchTitle.toUpperCase();
        String repo2 = repoName.toUpperCase();
        System.out.println(repo1 + " <-> " + repo2);
        return repo1.equals(repo2);
    }

    //more complex code with clicking pages
    public boolean isTotalResultsEqualToNumberProfilePictures(){
        int totalresults = getTotalNumberRepo();
        int numberProfilePictures = 0;
        List<WebElement> searchAvatars;

        while (!driver.findElements(btnNext).isEmpty()){
            searchAvatars = driver.findElements(searchAvatar);
            numberProfilePictures += searchAvatars.size();
            clickWaitByLocator(btnNext);
            pause(1); //time for elements reloading after clocking nextPage
        };

        //last or the one page
        searchAvatars = driver.findElements(searchAvatar);
        numberProfilePictures += searchAvatars.size();
        System.out.println("total results= " + totalresults + ", profiles: " + numberProfilePictures);
        return totalresults == numberProfilePictures;
    }



    public boolean isRepoInTheSearchResults(String repoName){
        List<WebElement> searchTitles;

        while (!driver.findElements(btnNext).isEmpty()){
            searchTitles = driver.findElements(searchTitle);
            for (WebElement title: searchTitles){
                if (isRopositoriesAreEquals(title.getText(), repoName))
                    return true;
            }
            System.out.println("next page");
            clickWaitByLocator(btnNext);
            pause(1); //time for elements reloading after clocking nextPage
        };

        //last or the one page
        searchTitles = driver.findElements(searchTitle);
        if (searchTitles.isEmpty())
            return false;

        for (WebElement title: searchTitles){
                if (isRopositoriesAreEquals(title.getText(), repoName))
                    return true;
        }

        return false;
    }

    public void scrollDown(){
        if (!driver.findElements(btnNext).isEmpty()) {
            Actions actions = new Actions(driver);
            actions.scrollToElement(driver.findElement(btnNext)).perform();
        }
    }
}

