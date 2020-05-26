package cz.vse.selenium.churchcrm;

import cz.vse.selenium.churchcrm.testframework.GridRow;
import cz.vse.selenium.churchcrm.testframework.model.DepositType;
import cz.vse.selenium.churchcrm.testframework.page.DepositPage.DepositListing;
import cz.vse.selenium.churchcrm.testframework.page.LoginPage.LoginPage;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static cz.vse.selenium.churchcrm.testframework.model.ShowEntries.E_100;
import static org.junit.jupiter.api.Assertions.*;

public class DepositTest extends AChurchCrmTest {


    private static final DateTimeFormatter depositGridFormatter = DateTimeFormatter.ofPattern("MM-dd-yy");
    private static final DateTimeFormatter depositEditFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final String DEPOSIT_ID_HEADER = "Deposit ID";

    @Test
    public void addDepositTest_CommentTypeAndDateShouldBeFilled_DepositShouldBeInTheGridDetailsShouldBeOkAndDepositsDeletionShouldWork() throws InterruptedException {

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


        // Then - Grid should contain row with filled deposit comment, deposit date and deposit type
        GridRow firstRow = depositListing.getDepositsGrid().search(uuid.toString()).get(0);
        HashMap<String, WebElement> firstRowValues = firstRow.getValues();
        assertAll(
                () -> assertEquals(uuid.toString(), firstRowValues.get("Deposit Comment").getText()),
                () -> assertEquals(depositDate.format(depositGridFormatter), firstRowValues.get("Deposit Date").getText()),
                () -> assertEquals(depositType.name(), firstRowValues.get("Deposit Type").getText())
        );

        //When Users goes to deposit detail
        depositListing.goToDetailDeposit(firstRow);

        //Then Deposit detail should contain correct deposit date and deposit comment
        assertAll(
                () -> assertEquals(depositDate.format(depositEditFormatter), driver.findElement(By.id("DepositDate")).getAttribute("value")),
                () -> assertEquals(uuid.toString(), driver.findElement(By.id("Comment")).getAttribute("value"))
        );

        //When User goes back to deposit listing sets show entries to 100, selects all rows and deletes them
        depositListing.goTo();
        depositListing.getDepositsGrid().changeShowEntriesTo(E_100);
        List<GridRow> oldRows = depositListing.getDepositsGrid().search("");
        List<String> oldRowsIds = extractIdsFromDepositRows(oldRows);
        oldRows.forEach(gridRow -> gridRow.getValues().get(DEPOSIT_ID_HEADER).click());
        depositListing.deleteSelectedRows();

        //Then All rows should be deleted
        List<String> newRowsIds = extractIdsFromDepositRows(depositListing.getDepositsGrid().search(""));
        assertTrue(oldRowsIds.stream().noneMatch(newRowsIds::contains));
    }

    private List<String> extractIdsFromDepositRows(List<GridRow> rows) {
        return rows
                .stream()
                .map(row -> row.getValues().get(DEPOSIT_ID_HEADER).getText())
                .collect(Collectors.toList());
    }
}
