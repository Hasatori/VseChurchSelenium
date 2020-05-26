package cz.vse.selenium.churchcrm.testframework.page.PeoplePage;

import cz.vse.selenium.churchcrm.testframework.Grid;
import cz.vse.selenium.churchcrm.testframework.page.APage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PeopleListingPage extends APage {

    private Grid peoplePageGrid;

    public PeopleListingPage(WebDriver driver) {
        super(driver,String.format("%s/v2/people",ROOT_URL));
        this.peoplePageGrid = new Grid(driver, By.cssSelector("#members_wrapper"), By.name("members_length"));
    }

    public Grid getPeoplePageGrid() {
        return peoplePageGrid;
    }
}
