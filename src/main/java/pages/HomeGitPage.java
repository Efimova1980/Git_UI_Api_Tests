package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class HomeGitPage extends BasePage{

    public HomeGitPage(WebDriver driver) {
        setDriver(driver);
        driver.get("https://github.com/");
        PageFactory.initElements(
                new AjaxElementLocatorFactory(driver, 10), this);
    }

    @FindBy(xpath = "//span[text()='Search']")
    WebElement searchForm;
    @FindBy(xpath = "//input[@placeholder='Search or jump to...']")
    WebElement inputSearch;

    public void openSearchForm(){
        clickWait(searchForm);
        clickWait(inputSearch);
    }

    public void typeTextToSearchForm(String text){
        inputSearch.sendKeys(text);
        inputSearch.sendKeys(Keys.ENTER);
    }
}