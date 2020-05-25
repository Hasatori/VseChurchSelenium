package cz.vse.selenium.churchcrm.testframework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class WebElement {


    protected final WebDriver driver;


    public WebElement(WebDriver driver) {
        this.driver = driver;
    }

}
