package cz.vse.selenium.churchcrm;

import cz.vse.selenium.churchcrm.testframework.GridRow;
import cz.vse.selenium.churchcrm.testframework.model.EventType;
import cz.vse.selenium.churchcrm.testframework.page.AddEditChurchEventPage;
import cz.vse.selenium.churchcrm.testframework.page.ChurchEventsPage;
import cz.vse.selenium.churchcrm.testframework.page.LoginPage;
import org.junit.jupiter.api.Test;
import java.time.Month;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

public class EventTest extends AChurchCrmTest {


    @Test
    public void eventTest_userAddEventAndGoesToEdit_EventShouldBeAddedAndShouldContainCorrectValues() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.goTo();
        loginPage.loginWithValidCredentials();
        AddEditChurchEventPage addEditChurchEventPage = new AddEditChurchEventPage(driver);
        addEditChurchEventPage.goTo();

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
        GridRow firstRowValues = churchEventsPage.getChurchEventsGrid(eventType, year, month).search(eventTitle.toString()).get(0);

        assertEquals(new ChurchEventsPage(driver).getUrl(), driver.getCurrentUrl());

        churchEventsPage.goToEdit(firstRowValues);

        assertAll(
                () -> assertSame(eventType, addEditChurchEventPage.getEventType()),
                () -> assertEquals(eventTitle.toString(), addEditChurchEventPage.getEventTitle()),
                () -> assertEquals(eventDescription, addEditChurchEventPage.getEventDescription()),
                () -> assertEquals(eventSermon, addEditChurchEventPage.getEventSermon()),
                () -> assertEquals(active, addEditChurchEventPage.getEventStatus())
        );

    }
}
