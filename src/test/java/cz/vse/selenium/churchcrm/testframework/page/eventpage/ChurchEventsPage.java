package cz.vse.selenium.churchcrm.testframework.page.eventpage;

import cz.vse.selenium.churchcrm.testframework.Grid;
import cz.vse.selenium.churchcrm.testframework.GridRow;
import cz.vse.selenium.churchcrm.testframework.model.EventType;
import cz.vse.selenium.churchcrm.testframework.page.APage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.time.Month;
import java.util.List;

public class ChurchEventsPage extends APage {

    private Grid churchEventsGrid;

    public ChurchEventsPage(WebDriver driver) {
        super(driver, String.format("%s/ListEvents.php", ROOT_URL));
        this.churchEventsGrid = new Grid(driver, By.cssSelector("#listEvents_wrapper"), By.cssSelector("#listEvents_length > label > select"));
    }

    public Grid getChurchEventsGrid(EventType eventType, Integer year, Month month) {
        new Select(driver.findElement(By.name("WhichType"))).selectByVisibleText(eventType.getValue1());
        new Select(driver.findElement(By.name("WhichYear"))).selectByVisibleText(year.toString());
        List<WebElement> gridBoxes = driver.findElements(By.cssSelector(".content .box"));
        Integer boxIndex = null;
        for (int i = 0; i < gridBoxes.size(); i++) {
            WebElement gridBoxCandidate = gridBoxes.get(i);
            if (gridBoxCandidate.getText().toLowerCase().contains(month.name().toLowerCase())) {
                boxIndex = i+2;
                break;
            }
        }
        if (boxIndex == null) {
            throw new IllegalStateException(String.format("No grid box found for %s %d %s", eventType, year, month));
        }
        String boxSelector = String.format(".content .box:nth-child(%s)", boxIndex);

        return new Grid(driver, By.cssSelector(String.format("%s #listEvents_wrapper", boxSelector)), By.cssSelector(String.format("%s #listEvents_length > label > select", boxSelector)));
    }

    public void goToEdit(GridRow gridRow) {
        gridRow.getValues().get("Action").findElement(By.name("EditEvent")).findElement(By.cssSelector("button")).click();
    }


}
