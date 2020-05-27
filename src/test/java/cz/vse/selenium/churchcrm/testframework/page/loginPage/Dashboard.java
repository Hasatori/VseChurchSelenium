package cz.vse.selenium.churchcrm.testframework.page.loginPage;

import cz.vse.selenium.churchcrm.testframework.page.APage;
import org.openqa.selenium.WebDriver;

public class Dashboard extends APage {


    public Dashboard(WebDriver driver) {
        super(driver, String.format("%s/Menu.php",ROOT_URL));
    }
}
