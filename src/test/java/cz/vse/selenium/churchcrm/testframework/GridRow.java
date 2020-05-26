package cz.vse.selenium.churchcrm.testframework;

import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GridRow extends WebPart {


    private final HashMap<String, String> values;

    public GridRow(WebDriver driver, HashMap<String, String> values) {
        super(driver);
        this.values = values;
    }

    public HashMap<String, String> getValues() {
        return values;
    }
}
