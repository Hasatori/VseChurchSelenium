package cz.vse.selenium.churchcrm.testframework.page;

import cz.vse.selenium.churchcrm.testframework.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class APage extends WebElement {
    protected final String url;
    protected static final String ROOT_URL = "https://digitalnizena.cz/church";
    protected WebDriverWait wait;

    public APage(WebDriver driver, String url) {
        super(driver);
        this.url = url;
        this.wait = new WebDriverWait(driver, 30);
    }

    public void goTo() {
        this.driver.get(url);
    }

    public String getUrl() {
        return url;
    }
}
