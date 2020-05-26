package cz.vse.selenium.churchcrm.testframework;

import org.openqa.selenium.WebDriver;
import java.util.List;

public class Grid extends WebPart {


    public Grid(WebDriver driver) {
        super(driver);
    }

    public List<GridRow> search(String searchValue) {
        return null;
    }
}
