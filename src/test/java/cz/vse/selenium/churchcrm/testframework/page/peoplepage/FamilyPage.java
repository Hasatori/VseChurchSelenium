package cz.vse.selenium.churchcrm.testframework.page.peoplepage;

import cz.vse.selenium.churchcrm.testframework.page.APage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class FamilyPage extends APage {

    public FamilyPage(WebDriver driver)  {super(driver, String.format("%s/FamilyEditor.php", ROOT_URL)); }

    public void addFamilyInfo (String familyName, String addressFirst, String addressSecond, String city, String zip, String state, String country, Integer latitude, Integer longitude) {

        driver.findElement(By.cssSelector("#FamilyName")).sendKeys(familyName);
        driver.findElement(By.name("Address1")).sendKeys(addressFirst);
        driver.findElement(By.name("Address2")).sendKeys(addressSecond);
        driver.findElement(By.name("City")).sendKeys(city);
        driver.findElement(By.name("Zip")).sendKeys(zip);
        new Select(driver.findElement(By.id("state-input"))).selectByVisibleText(state);
        new Select(driver.findElement(By.id("country-input"))).selectByVisibleText(country);


        WebElement latitudeElement = driver.findElement(By.name("Latitude"));
        latitudeElement.clear();
        latitudeElement.sendKeys(latitude.toString());

        WebElement longitudeElement = driver.findElement(By.name("Longitude"));
        longitudeElement.clear();
        longitudeElement.sendKeys(longitude.toString());

    }
    public void saveFamilyInfo() { driver.findElement(By.name("FamilySubmit")).click(); }

    public void addNewFamilyMember() {driver.findElement(By.className("fa-plus-square")).click(); }
}
