package manager;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

@Getter
public class AppManager {
    private WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setup(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterMethod(enabled = true, alwaysRun = true)
    public void tearDown(){
        if (driver != null)
            driver.quit();
    }
}
