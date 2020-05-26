package cz.vse.selenium.churchcrm.testframework;

import cz.vse.selenium.churchcrm.testframework.model.ShowEntries;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Grid extends WebPart {

    private static final By SEARCH_INPUT_SELECTOR = By.cssSelector(".dataTables_filter input");
    private static final By GRID_HEAD_SELECTOR = By.cssSelector("div > table > thead th");
    private static final By GRID_ROWS_SELECTOR = By.cssSelector("div > table > tbody > tr");
    private static final By GRID_ROW_CELLS_SELECTOR = By.cssSelector(":scope  > td");
    private final By gridLayoutSelector, showEntriesSelector;


    public Grid(WebDriver driver, By gridLayoutSelector, By showEntriesSelector) {
        super(driver);
        this.gridLayoutSelector = gridLayoutSelector;
        this.showEntriesSelector = showEntriesSelector;
    }

    public List<GridRow> search(String query) {

        WebElement gridWrapper = driver.findElement(gridLayoutSelector);
        WebElement searchInput = gridWrapper.findElement(SEARCH_INPUT_SELECTOR);
        searchInput.clear();
        searchInput.sendKeys(query);
        List<String> header = getHeader(gridWrapper);
        waitForRowToLoad(header, gridWrapper);
        return getRows(header, gridWrapper);
    }

    public void changeShowEntriesTo(ShowEntries showEntries) {
        new Select(driver.findElement(showEntriesSelector)).selectByVisibleText(showEntries.getValue().toString());
    }

    private void waitForRowToLoad(List<String> header, WebElement gridWrapper) {
        new WebDriverWait(driver, 5)
                .until((ExpectedCondition<Boolean>) webDriver -> Optional.ofNullable(gridWrapper.findElements(GRID_ROWS_SELECTOR).get(0))
                        .map(row -> row.findElements(GRID_ROW_CELLS_SELECTOR).size() == header.size()).get());
    }


    private List<String> getHeader(WebElement gridWrapper) {
        return gridWrapper
                .findElements(GRID_HEAD_SELECTOR)
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    private List<GridRow> getRows(List<String> header, WebElement gridWrapper) {
        return gridWrapper
                .findElements(GRID_ROWS_SELECTOR)
                .stream()
                .map((rowElement) -> new GridRow(driver, getRowValues(header, rowElement)))
                .collect(Collectors.toList());
    }

    private HashMap<String,WebElement> getRowValues(List<String> header, WebElement rowElement) {
        HashMap<String, WebElement> rowValues = new HashMap<>();
        List<WebElement> cells = rowElement.findElements(GRID_ROW_CELLS_SELECTOR);
        for (int i = 0; i < cells.size(); i++) {
            rowValues.put(header.get(i), cells.get(i));
        }
        return rowValues;
    }

}
