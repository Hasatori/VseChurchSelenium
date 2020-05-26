package cz.vse.selenium.churchcrm;

import cz.vse.selenium.churchcrm.testframework.model.GenderType;
import cz.vse.selenium.churchcrm.testframework.page.LoginPage;
import cz.vse.selenium.churchcrm.testframework.page.PeoplePage;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PeopleTest extends AChurchCrmTest {

    @Test
    public void addPersonTest_AddNewPersonAndVerifyPersonalInfo(){
        //Given - User is on the login page and succesfully login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.goTo();
        loginPage.loginWithValidCredentials();

        //When - User choose People in menu, then click Add New Person and fill all Personal info without Last Name and then click to Save button
        driver.findElement(By.linkText("People")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Add New Person"))).click();

        PeoplePage peoplePage = new PeoplePage(driver);
        peoplePage.goTo();
        peoplePage.addPersonalInfo(GenderType.Male, "Mr.", "George", "Dave", "", "Dis.", "April", 12, 1999, true);
        peoplePage.savePersonalInfo();

        //Then - New Person is not save and user see error message on page Person Editor
        assertEquals(driver.findElement(By.tagName("font"));
    }
}
