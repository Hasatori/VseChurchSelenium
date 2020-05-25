package cz.vse.selenium.churchcrm.testframework.page;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginPage extends APage {

    public static String VALID_USERNAME = "church";
    public static String VALID_PASSWORD = "church12345";

    public LoginPage(WebDriver driver) {
        super(driver, String.format("%s/Login.php", ROOT_URL));
    }

    public void login(String username, String password) {
        driver.findElement(By.cssSelector("#UserBox"))
                .sendKeys(username);
        driver.findElement(By.cssSelector("#PasswordBox"))
                .sendKeys(password);
        driver.findElement(By.cssSelector(".btn-primary"))
                .click();
    }

    public void loginWithValidCredentials() {
        login(VALID_USERNAME, VALID_PASSWORD);
    }

    public void assertLoginErrorMessageIsShown() {
        assertTrue(this.wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("#Login .alert"), "Invalid login or password")));
    }

}


