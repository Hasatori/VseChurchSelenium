package cz.vse.selenium.churchcrm.testframework.page.depositpage;

import cz.vse.selenium.churchcrm.testframework.page.APage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DepositEditingPage extends APage {
    private static final DateTimeFormatter depositEditFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public DepositEditingPage(WebDriver driver, Integer id) {
        super(driver, String.format("%s/?DepositSlipID=%d", ROOT_URL, id));
    }


    public void assertDepositDateValue(LocalDateTime expectedDate) {
        assertEquals(expectedDate.format(depositEditFormatter), driver.findElement(By.id("DepositDate")).getAttribute("value"));
    }

    public void assertCommentValue(String expectedValue) {
        assertEquals(expectedValue, driver.findElement(By.id("Comment")).getAttribute("value"));
    }
}
