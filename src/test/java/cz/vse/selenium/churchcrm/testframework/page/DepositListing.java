package cz.vse.selenium.churchcrm.testframework.page;

import cz.vse.selenium.churchcrm.testframework.Deposit;
import org.openqa.selenium.WebDriver;

public class DepositListing extends APage {

    public DepositListing(WebDriver driver) {
        super(driver, String.format("%s/FindDepositSlip.php",ROOT_URL));
    }


    public void addDeposit(Deposit deposit){

    }
}
