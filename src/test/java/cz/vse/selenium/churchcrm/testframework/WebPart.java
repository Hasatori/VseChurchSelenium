package cz.vse.selenium.churchcrm.testframework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class WebPart {


    protected final WebDriver driver;


    public WebPart(WebDriver driver) {
        this.driver = driver;
    }

}
