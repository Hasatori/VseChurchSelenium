package cz.vse.selenium.churchcrm;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class AChurchCrmTest {

    protected WebDriverWait wait;
    protected WebDriver driver;

    static {
        System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver.exe");
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
        this.driver.manage().window().maximize();
        this.wait = new WebDriverWait(driver, 30);
    }
    
    @AfterEach
    public void afterEach() {

        driver.quit();
    }


}

