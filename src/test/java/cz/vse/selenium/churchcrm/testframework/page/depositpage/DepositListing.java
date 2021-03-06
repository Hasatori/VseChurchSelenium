package cz.vse.selenium.churchcrm.testframework.page.depositpage;

import cz.vse.selenium.churchcrm.testframework.model.DepositType;
import cz.vse.selenium.churchcrm.testframework.Grid;
import cz.vse.selenium.churchcrm.testframework.GridRow;
import cz.vse.selenium.churchcrm.testframework.page.APage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DepositListing extends APage {

    private static final DateTimeFormatter depositDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final String GRID_LAYOUT_SELECTOR = "#depositsTable_wrapper";
    private static final String SHOW_ENTRIES_SELECTOR = "depositsTable_length";
    private final Grid depositsGrid;


    public DepositListing(WebDriver driver) {
        super(driver, String.format("%s/FindDepositSlip.php", ROOT_URL));
        this.depositsGrid = new Grid(driver, By.cssSelector(GRID_LAYOUT_SELECTOR), By.name(SHOW_ENTRIES_SELECTOR));
    }


    public void addDeposit(String depositComment, DepositType depositType, LocalDateTime depositDate) {
        WebElement webElement = driver.findElement(By.id("depositDate"));
        webElement.click();
        webElement.clear();
        webElement.sendKeys(depositDate.format(depositDateFormatter));
        WebElement commentElement = driver.findElement(By.id("depositComment"));
        commentElement.clear();
        commentElement.sendKeys(depositComment);
        new Select(driver.findElement(By.id("depositType"))).selectByVisibleText(depositType.name());
        driver.findElement(By.id("addNewDeposit")).click();
    }

    public Grid getDepositsGrid() {
        return depositsGrid;
    }

    public void deleteSelectedRows() {
        driver.findElement(By.cssSelector("#deleteSelectedRows")).click();
        String modalSelector=".modal-dialog";
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(String.format("%s .bootbox-accept",modalSelector)))).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(modalSelector)));
    }

    public DepositEditingPage goToDepositDetailFromGridRow(GridRow gridRow) {
        WebElement depositIdCell = gridRow.getValues().get("Deposit ID");
        Integer id = Integer.valueOf(depositIdCell.getText());
        depositIdCell.findElement(By.cssSelector(".fa-inverse")).click();
        return new DepositEditingPage(driver, id);
    }
}
