package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public abstract class BasePage {
    static WebDriver driver;

    public static void setDriver(WebDriver wd){
        driver = wd;
    }

    public  void pause(int time){
        try {
            Thread.sleep(time * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isElementDisplayed(WebElement element){
        return element.isDisplayed();
    }

    public boolean isElementPresent(By locator){
        return !driver.findElements(locator).isEmpty();
    }

    public boolean isTextInElementPresent(WebElement element, String text){
        return element.getText().contains(text);
    }

    public void clickWait(WebElement element){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    public void clickWaitByLocator(By locator){
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

}
