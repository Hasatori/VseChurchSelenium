package cz.vse.selenium.churchcrm;

import cz.vse.selenium.churchcrm.testframework.GridRow;
import cz.vse.selenium.churchcrm.testframework.model.GenderType;
import cz.vse.selenium.churchcrm.testframework.page.FamilyPage;
import cz.vse.selenium.churchcrm.testframework.page.LoginPage;
import cz.vse.selenium.churchcrm.testframework.page.PeopleListingPage;
import cz.vse.selenium.churchcrm.testframework.page.PeoplePage;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class PeopleTest extends AChurchCrmTest {

    @Test
    public void addPersonTest_AddNewPersonAndVerifyPersonalInfo() {
        //Given - User is on the login page and successfully login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.goTo();
        loginPage.loginWithValidCredentials();

        //When - User choose People in menu, then click Add New Person and fill all Personal info without Last Name and then click to Save button
        driver.findElement(By.linkText("People")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Add New Person"))).click();

        PeoplePage peoplePage = new PeoplePage(driver);
        peoplePage.goTo();
        peoplePage.addPersonalInfo(GenderType.Male, "Mr.", "George", "Dave", "", "Dis.", "April", 12, 1999, true);

        //Then - New Person is not save and user see error message on page Person Editor
        assertTrue(driver.findElement(By.cssSelector(".alert-danger")).getText().contains("Invalid fields or selections. Changes not saved! Please correct and try again!"));
    }

    @Test
    public void addFamilyTest_AddNewFamilyWithoutFamilyName_Incorrect() {
        // Given - User is on the login page and successfully login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.goTo();
        loginPage.loginWithValidCredentials();

        // When - User go through lef menu and choose People, Add New family. Then fill form with new family and add new family member.
        driver.findElement(By.linkText("People")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Add New Family"))).click();

        FamilyPage familyPage = new FamilyPage(driver);
        familyPage.goTo();
        UUID uuid = UUID.randomUUID();

        String country = "Belize";
        familyPage.addFamilyInfo(uuid.toString(), "Old town square", "Rimmer", "Prague", "110 00", "Alabama", country, 30, 30);
        familyPage.saveFamilyInfo();
        familyPage.addNewFamilyMember();


        PeoplePage peoplePage = new PeoplePage(driver);
        String firstName = "George";
        String middleName = "Dave";
        String lastName = "";

        peoplePage.addPersonalInfo(GenderType.Male, "Mr.", firstName, middleName, lastName, "Dis.", "April", 12, 1999, true);

        // Then - Check that new person was added to system
        assertEquals(String.format("%s %s %s", firstName, middleName, uuid.toString()), driver.findElement(By.cssSelector("h3.profile-username")).getText());

        // When - User press button Edit and change mail
        peoplePage.editPersonaInfo();
        String email = "example@example.com";
        peoplePage.editPersonaPage(email);

        // Then - Check that mail was changed and is visible on Persona profile
        assertAll(
                () -> assertEquals(email, driver.findElement(By.cssSelector(".box-primary .fa-envelope ~span a ")).getText()),
                () -> assertEquals(1, driver.findElements(By.cssSelector(".timeline li > .fa-pencil")).size())
        );

        // When - User search added person by view all persons
        PeopleListingPage peopleListingPage = new PeopleListingPage(driver);
        peopleListingPage.goTo();
        GridRow gridRow = peopleListingPage.getPeoplePageGrid().search(uuid.toString()).get(0);

        //  Then - Added person was found and is checked by persons first name, last name and mail
        assertAll(
                () -> assertEquals(firstName, gridRow.getValues().get("First Name").getText()),
                () -> assertEquals(uuid.toString(), gridRow.getValues().get("Last Name").getText()),
                () -> assertEquals(email, gridRow.getValues().get("Email").getText())
        );
    }
}
