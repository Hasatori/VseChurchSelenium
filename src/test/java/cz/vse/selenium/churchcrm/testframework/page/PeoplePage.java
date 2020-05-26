package cz.vse.selenium.churchcrm.testframework.page;


import cz.vse.selenium.churchcrm.testframework.model.GenderType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.time.Month;
import java.time.MonthDay;
import java.time.Year;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PeoplePage extends APage {


    public PeoplePage(WebDriver driver) {
        super(driver, String.format("%s/PersonEditor.php", ROOT_URL));
    }

    public void addPersonalInfo(GenderType genderType, String title, String firstName, String middleName, String lastName, String suffix, String birthMonth, Integer birthDay, Integer birthYear, Boolean hideAge){

        new Select(driver.findElement(By.name("Gender"))).selectByVisibleText(genderType.name());
        driver.findElement(By.cssSelector("#Title")).sendKeys(title);
        driver.findElement(By.cssSelector("#FirstName")).sendKeys(firstName);
        driver.findElement(By.cssSelector("#MiddleName")).sendKeys(middleName);
        driver.findElement(By.cssSelector("#LastName")).sendKeys(lastName);
        driver.findElement(By.cssSelector("#Suffix")).sendKeys(suffix);
        new Select(driver.findElement(By.name("BirthMonth"))).selectByVisibleText(birthMonth);
        new Select(driver.findElement(By.name("BirthDay"))).selectByVisibleText(birthDay.toString());
        WebElement birthYearElement = driver.findElement(By.name("BirthYear"));
        birthYearElement.clear();
        birthYearElement.sendKeys(birthYear.toString());
        if(hideAge){
            driver.findElement(By.name("HideAge")).click();
        }
    }

    public void savePersonalInfo(){
        driver.findElement(By.cssSelector("#PersonSaveButton")).click();
    }

//    public void getFontColor(WebElement element){
//        return element.getCssValue("red");
//    }
}


