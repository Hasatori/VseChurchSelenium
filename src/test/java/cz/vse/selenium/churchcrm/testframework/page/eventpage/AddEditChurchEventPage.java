package cz.vse.selenium.churchcrm.testframework.page.eventpage;

import cz.vse.selenium.churchcrm.testframework.model.EventType;
import cz.vse.selenium.churchcrm.testframework.page.APage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class AddEditChurchEventPage extends APage {

    private static final By EVENT_TYPE_SELECTOR = By.name("EventTypeName");
    private static final By EVENT_TITLE_SELECTOR = By.name("EventTitle");
    private static final By EVENT_STATUSES_SELECTOR = By.name("EventStatus");
    private static final By EVENT_DESC_SELECTOR = By.name("EventDesc");

    public AddEditChurchEventPage(WebDriver driver) {
        super(driver, String.format("%s/EventEditor.php", ROOT_URL));
    }

    public void createNewEvent(EventType eventType, String eventTitle, String eventDesc, String from, String eventSermon, Boolean eventStatus) {
        new Select(driver.findElement(By.id("event_type_id"))).selectByVisibleText(eventType.getValue1());
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(EVENT_TITLE_SELECTOR));

        WebElement dateRange = driver.findElement(By.name("EventDateRange"));
        dateRange.clear();
        dateRange.sendKeys(from);
        WebElement eventTitleElement = driver.findElement(EVENT_TITLE_SELECTOR);
        eventTitleElement.clear();
        eventTitleElement.sendKeys(eventTitle);
        driver.findElement(EVENT_DESC_SELECTOR).sendKeys(eventDesc);
        driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
        driver.findElement(By.cssSelector("body")).sendKeys(eventSermon);
        driver.switchTo().defaultContent();
        List<WebElement> statuses = driver.findElements(EVENT_STATUSES_SELECTOR);
        if (eventStatus) {
            statuses.get(0).click();
        } else {
            statuses.get(1).click();
        }
        driver.findElement(By.name("SaveChanges")).click();
    }

    public EventType getEventType() {
        return EventType.customValueOf(driver.findElement(EVENT_TYPE_SELECTOR).getAttribute("value"));
    }

    public String getEventSermon() {
        driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
        String result = driver.findElement(By.cssSelector("body")).getText();
        driver.switchTo().defaultContent();
        return result;
    }

    public String getEventTitle() {
        return driver.findElement(EVENT_TITLE_SELECTOR).getAttribute("value");

    }

    public String getEventDescription() {
        return driver.findElement(EVENT_DESC_SELECTOR).getText();
    }

    public Boolean getEventStatus() {
        List<WebElement> statuses = driver.findElements(EVENT_STATUSES_SELECTOR);
        return statuses.get(0).isSelected();

    }
}
