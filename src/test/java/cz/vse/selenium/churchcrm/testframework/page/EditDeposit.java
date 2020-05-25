package cz.vse.selenium.churchcrm.testframework.page;

import org.openqa.selenium.WebDriver;

public class EditDeposit extends APage {


    public EditDeposit(WebDriver driver, Integer depositId) {
        super(driver, String.format("%s/DepositSlipEditor.php?DepositSlipID=%d", ROOT_URL, depositId));
    }
}
