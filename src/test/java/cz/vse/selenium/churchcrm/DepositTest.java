package cz.vse.selenium.churchcrm;

import cz.vse.selenium.churchcrm.testframework.model.DepositType;
import cz.vse.selenium.churchcrm.testframework.page.DepositListing;
import cz.vse.selenium.churchcrm.testframework.page.LoginPage;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.UUID;

import static cz.vse.selenium.churchcrm.testframework.model.ShowEntries.E_100;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DepositTest extends AChurchCrmTest {


    private static final DateTimeFormatter depositGridFormatter = DateTimeFormatter.ofPattern("MM-dd-YY");


    @Test
    public void addDepositTest_CommentTypeAndDateFilled_DepositShouldBeFoundInTheGrid() throws InterruptedException {

        // Given - User is logged in and is on deposit page
        LoginPage loginPage = new LoginPage(driver);
        loginPage.goTo();
        loginPage.loginWithValidCredentials();
        DepositListing depositListing = new DepositListing(driver);
        depositListing.goTo();

        // When - User fills deposit comment deposit type and deposit date and adds new deposit
        UUID uuid = UUID.randomUUID();
        DepositType depositType = DepositType.Cash;
        LocalDateTime depositDate = LocalDateTime.now().minusDays(3);
        depositListing.addDeposit(uuid.toString(), depositType, depositDate);

        depositListing.getDepositsGrid().changeShowEntriesTo(E_100);
        depositListing.getDepositsGrid().search("").forEach(gridRow -> gridRow.getValues().get("Deposit ID").click());

        // Then - Grid should contain row with filled deposit comment, deposit date and deposit type
        HashMap<String, WebElement> firstRowValues = depositListing.getDepositsGrid().search(uuid.toString()).get(0).getValues();
        assertAll(
                () -> assertEquals(uuid.toString(), firstRowValues.get("Deposit Comment").getText()),
                () -> assertEquals(depositDate.format(depositGridFormatter), firstRowValues.get("Deposit Date").getText()),
                () -> assertEquals(depositType.name(), firstRowValues.get("Deposit Type").getText())
        );


    }
}
