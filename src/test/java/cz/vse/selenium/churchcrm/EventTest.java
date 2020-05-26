package cz.vse.selenium.churchcrm;

import cz.vse.selenium.churchcrm.testframework.GridRow;
import cz.vse.selenium.churchcrm.testframework.model.EventType;
import cz.vse.selenium.churchcrm.testframework.page.AddEditChurchEventPage;
import cz.vse.selenium.churchcrm.testframework.page.ChurchEventsPage;
import cz.vse.selenium.churchcrm.testframework.page.LoginPage;
import org.junit.jupiter.api.Test;

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
        Boolean active = false;
        addEditChurchEventPage.createNewEvent(eventType, eventTitle.toString(), eventDescription, active);


        ChurchEventsPage churchEventsPage = new ChurchEventsPage(driver);
        GridRow firstRowValues = churchEventsPage.getChurchEventsGrid().search(eventTitle.toString()).get(0);

        assertEquals(new ChurchEventsPage(driver).getUrl(), driver.getCurrentUrl());

        churchEventsPage.goToEdit(firstRowValues);
        assertAll(
                () -> assertSame(eventType, addEditChurchEventPage.getEventType()),
                () -> assertEquals(eventTitle.toString(), addEditChurchEventPage.getEventTitle()),
                () -> assertEquals(eventDescription, addEditChurchEventPage.getEventDescription()),
                () -> assertEquals(active, addEditChurchEventPage.getEventStatus())

        );

    }
}
