package cz.vse.selenium.churchcrm.testframework.page.LoginPage;

import cz.vse.selenium.churchcrm.testframework.page.APage;
import org.openqa.selenium.WebDriver;

public class ChangePassword extends APage {

    public ChangePassword(WebDriver driver) {
        super(driver,String.format("%s/external/password/",ROOT_URL));
    }
}
