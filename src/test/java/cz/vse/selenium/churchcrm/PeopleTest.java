package cz.vse.selenium.churchcrm;

import cz.vse.selenium.churchcrm.testframework.model.GenderType;
import cz.vse.selenium.churchcrm.testframework.page.FamilyPage;
import cz.vse.selenium.churchcrm.testframework.page.LoginPage;
import cz.vse.selenium.churchcrm.testframework.page.PeoplePage;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

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
        assertTrue(driver.findElement(By.cssSelector(".alert-danger")).getText().contains("Invalid fields or selections. Changes not saved! Please correct and try again!"));
    }

    @Test
    public void addFamilyTest_AddNewFamilyWithoutFamilyName_Incorrect(){
        // Given
        LoginPage loginPage = new LoginPage(driver);
        loginPage.goTo();
        loginPage.loginWithValidCredentials();

        // When
        driver.findElement(By.linkText("People")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Add New Family"))).click();

        FamilyPage familyPage = new FamilyPage(driver);
        familyPage.goTo();
        UUID uuid = UUID.randomUUID();

        familyPage.addFamilyInfo(uuid.toString(),"Old town square", "Rimmer", "Prague", "110 00", "Alabama", "Belize", 30, 30);
        familyPage.saveFamilyInfo();
        familyPage.addNewFamilyMember();

        PeoplePage peoplePage = new PeoplePage(driver);
        peoplePage.addPersonalInfo(GenderType.Male, "Mr.", "George", "Dave", "", "Dis.", "April", 12, 1999, true);
        peoplePage.savePersonalInfo();


        // Then
        /*assertAll(
                () -> assertEquals(By.tagName("h1"))
                () -> assertEquals(loginPage.getUrl(), driver.getCurrentUrl()),
        );*/

        // When
        peoplePage.editPersonaInfo();
        peoplePage.editPersonaPage("example@example.com");
        peoplePage.savePersonalInfo();

        // Then
        // Assert - ověřit mail, ověřit timeline

        // When
        driver.findElement(By.linkText("People")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("View All Persons"))).click();
    }
}
