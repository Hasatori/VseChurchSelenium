package cz.vse.selenium.churchcrm.testframework.page;

import cz.vse.selenium.churchcrm.testframework.Grid;
import cz.vse.selenium.churchcrm.testframework.GridRow;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.time.Month;

public class ChurchEventsPage extends APage {

    private Grid churchEventsGrid;

    public ChurchEventsPage(WebDriver driver) {
        super(driver, String.format("%s/ListEvents.php",ROOT_URL));
        this.churchEventsGrid = new Grid(driver, By.cssSelector("#listEvents_wrapper"), By.cssSelector("#listEvents_length > label > select"));
    }

    public Grid getChurchEventsGrid() {
        return churchEventsGrid;
    }

    public void goToEdit(GridRow gridRow){
        gridRow.getValues().get("Action").findElement(By.name("EditEvent")).findElement(By.cssSelector("button")).click();
    }
}
