package cz.vse.selenium.churchcrm.testframework.page.peoplePage;


import cz.vse.selenium.churchcrm.testframework.model.GenderType;
import cz.vse.selenium.churchcrm.testframework.page.APage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class EditPersonPage extends APage {


    public EditPersonPage(WebDriver driver) {
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
        driver.findElement(By.cssSelector("#PersonSaveButton")).click();
    }


    public void editPersonaInfo() { driver.findElement(By.id("EditPerson")).click(); }

    public void editPersonaPage(String mail) {
        driver.findElement(By.name("Email")).sendKeys(mail);
    }

//    public void getFontColor(WebElement element){
//        return element.getCssValue("red");
//    }
}


