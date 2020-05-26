package cz.vse.selenium.churchcrm;

import cz.vse.selenium.churchcrm.testframework.GridRow;
import cz.vse.selenium.churchcrm.testframework.model.EventType;
import cz.vse.selenium.churchcrm.testframework.page.EventPage.AddEditChurchEventPage;
import cz.vse.selenium.churchcrm.testframework.page.EventPage.ChurchEventsPage;
import cz.vse.selenium.churchcrm.testframework.page.LoginPage.LoginPage;
import org.junit.jupiter.api.Test;
import java.time.Month;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

public class EventTest extends AChurchCrmTest {

    @Test
    public void eventTest_userAddEventAndGoesToEdit_EventShouldBeAddedAndShouldContainCorrectValues() throws InterruptedException {
        // Given - User is logged in and is on event page
        LoginPage loginPage = new LoginPage(driver);
        loginPage.goTo();
        loginPage.loginWithValidCredentials();
        AddEditChurchEventPage addEditChurchEventPage = new AddEditChurchEventPage(driver);
        addEditChurchEventPage.goTo();

        // When - User fills page and made new event
        EventType eventType = EventType.Church_Service;
        UUID eventTitle = UUID.randomUUID();
        String eventDescription = "Event description";
        Integer year = 2021;
        Month month = Month.APRIL;
        String from = String.format("%d-%d-27 10:30 PM", year, month.getValue());
        String eventSermon = "test";
        Boolean active = false;
        addEditChurchEventPage.createNewEvent(eventType, eventTitle.toString(), eventDescription, from, eventSermon, active);
        ChurchEventsPage churchEventsPage = new ChurchEventsPage(driver);

        // Then - Check event was made by check URL address
        assertEquals(new ChurchEventsPage(driver).getUrl(), driver.getCurrentUrl());

        // When - User search event that was made
        GridRow firstRowValues = churchEventsPage.getChurchEventsGrid(eventType, year, month).search(eventTitle.toString()).get(0);
        churchEventsPage.goToEdit(firstRowValues);

        // Then - Event check by eventType etc. as asserts
        assertAll(
                () -> assertSame(eventType, addEditChurchEventPage.getEventType()),
                () -> assertEquals(eventTitle.toString(), addEditChurchEventPage.getEventTitle()),
                () -> assertEquals(eventDescription, addEditChurchEventPage.getEventDescription()),
                () -> assertEquals(eventSermon, addEditChurchEventPage.getEventSermon()),
                () -> assertEquals(active, addEditChurchEventPage.getEventStatus())
        );
    }
}
