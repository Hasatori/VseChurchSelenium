package cz.vse.selenium.churchcrm.testframework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GridRow extends WebPart {


    private final HashMap<String, WebElement> values;

    public GridRow(WebDriver driver, HashMap<String, WebElement> values) {
        super(driver);
        this.values = values;
    }

    public HashMap<String,WebElement> getValues() {
        return values;
    }
}
